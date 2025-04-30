from django.shortcuts import get_object_or_404
from django.shortcuts import render
import bcrypt
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from roles.models import Role
from users.serializers import UserSerializer
from users.models import User, UserHasRoles
from rest_framework_simplejwt.tokens import RefreshToken
# Create your views here.
#aquí se van a crear las operaciones del CRUD

#status 200 para respuestas exitosas
#400 o 500 para los errores
@api_view(['POST'])
def register(request):
    serializer = UserSerializer(data = request.data)
    if serializer.is_valid():
        user = serializer.save()
        
        client_role = get_object_or_404(Role, id = 'CLIENT')
        UserHasRoles.objects.create(id_user=user, id_rol = client_role)
        
        return Response(serializer.data, status = status.HTTP_201_CREATED)
    return Response(serializer.errors, status = status.HTTP_400_BAD_REQUEST)
 #Metodo POST el cual valida el login del usuario por medio de validaciones
@api_view(['POST'])
def login(request):
    email = request.data.get('email')
    password = request.data.get('password')
    
    if not email or not password:
        return Response({"error": "El email y el password son obligatorios"}, status = status.HTTP_400_BAD_REQUEST)
    try: 
        user = User.objects.get(email = email)
    except User.DoesNotExist:
        return Response({"error": "El email o el password no son validos"}, status=status.HTTP_401_UNAUTHORIZED)
    
    #Aquí se hace una comparación de los password encriptado y el no encriptado
    if bcrypt.checkpw(password.encode('utf-8'), user.password.encode('utf-8')):
        refresh_token = RefreshToken.for_user(user)
        access_token = str(refresh_token.access_token) #aquí se genera el token de autenticación único para cada usuario
        user_data= {
            "user": {
                "id": user.id,
                "name": user.name,
                "lastname": user.lastname,
                "email": user.email,
                "phone": user.phone,
                "image": user.phone,
                "notification_token": user.notification_token,
            },
            "token": 'Bearer ' + access_token
        }
        return Response(user_data, status = status.HTTP_200_OK)
    else:
        return Response({"error": "El email o el password no son validos"}, status=status.HTTP_401_UNAUTHORIZED)
