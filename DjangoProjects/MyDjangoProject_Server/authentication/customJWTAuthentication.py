from rest_framework_simplejwt.authentication import JWTAuthentication
from rest_framework.exceptions import AuthenticationFailed

from users.models import User

class CustomJWTAuthentication(JWTAuthentication):
    def get_user(self, validated_token):
        try:
            user_id = validated_token['id']
        except KeyError:
            raise AuthenticationFailed('Token contained no recognizable user identification')
        try:
            user = User.objects.get(id=user_id)
        except User.DoesNotExist:
            raise AuthenticationFailed('TUsuario no encontrado')
        user.is_authenticated = True
        return user
#Esto de aquí lo que hace es usar un token JWT custom ya que tenemos un desmadre con las tablas y los nombres que se generan automáticamente y el token que no lo recibe django
#Entonces se tiene que hacer una comparación de los tokens por medio de esta clase para que django reconozca que son los mismos campos