from django.conf import settings
from django.shortcuts import get_object_or_404
from django.shortcuts import render
import bcrypt
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework import status
from roles.models import Role
from roles.serializers import RoleSerializer
from users.serializers import UserSerializer
from users.models import User, UserHasRoles
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework.permissions import AllowAny
# Create your views here.
#aquí se van a crear las operaciones del CRUD
#este es un cambio
#status 200 para respuestas exitosas
#400 o 500 para los errores


def getCustomTokenForUser(user):
    refresh_token = RefreshToken.for_user(user)
    del refresh_token.payload['user_id']
    refresh_token.payload['id'] = user.id
    refresh_token.payload['name'] = user.name
    return refresh_token


@api_view(['POST'])
@permission_classes([AllowAny])
def register(request):
    serializer = UserSerializer(data = request.data)
    if serializer.is_valid():
        user = serializer.save()
        
        client_role = get_object_or_404(Role, id = 'CLIENT')
        UserHasRoles.objects.create(id_user=user, id_rol = client_role)
        
        roles = Role.objects.filter(userhasroles__id_user=user)
        roles_serializer = RoleSerializer(roles, many=True)
        response_data={
            **serializer.data,
            'roles': roles_serializer.data
        }
        
        return Response(response_data, status = status.HTTP_201_CREATED)
    #manejaremos los errores de esta manera:
    error_messages=[]
    for field, errors in serializer.errors.items():
        for error in errors:
            error_messages.append(f"{field}: {error}")
            
    error_response = {
        "message": error_messages,
        "statusCode":status.HTTP_400_BAD_REQUEST
    }
    
    return Response(error_response, status = status.HTTP_400_BAD_REQUEST)
 #Metodo POST el cual valida el login del usuario por medio de validaciones
@api_view(['POST'])
@permission_classes([AllowAny])
def login(request):
    email = request.data.get('email')
    password = request.data.get('password')
     
    if not email or not password:
        return Response(
            {
                "message": "El email y el password son obligatorios",
                "statusCode": status.HTTP_400_BAD_REQUEST
            },
            status = status.HTTP_400_BAD_REQUEST
        )
    try: 
        user = User.objects.get(email = email)
    except User.DoesNotExist:
        return Response(
            {
                "message": "El email y el password no son validos",
                "statusCode": status.HTTP_401_UNAUTHORIZED
            },
            status = status.HTTP_401_UNAUTHORIZED
        )
    
    #Aquí se hace una comparación de los password encriptado y el no encriptado
    if bcrypt.checkpw(password.encode('utf-8'), user.password.encode('utf-8')):
        refresh_token = getCustomTokenForUser(user)
        access_token = str(refresh_token.access_token) #aquí se genera el token de autenticación único para cada usuario
        roles = Role.objects.filter(userhasroles__id_user=user)
        roles_serializer = RoleSerializer(roles, many=True)
        
        user_data= {
            "user": {
                "id": user.id,
                "name": user.name,
                "lastname": user.lastname,
                "email": user.email,
                "phone": user.phone,
                "image": f'http://{settings.GLOBAL_IP}:{settings.GLOBAL_HOST}{user.image}' if user.image else None,
                "notification_token": user.notification_token,
                "roles": roles_serializer.data,
            },
            "token": 'Bearer ' + access_token
        }
        return Response(user_data, status = status.HTTP_200_OK)
    else:
        return Response(
            {
                "message": "El email y el password no son validos",
                "statusCode": status.HTTP_401_UNAUTHORIZED
            },
            status = status.HTTP_401_UNAUTHORIZED
        )
