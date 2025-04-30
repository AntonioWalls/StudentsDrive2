from django.db import models
#Si no andamos mal aquí lo que andamos creando es una tabla de relación la cual va a tener la relación de muchos a muchos entre las tablas de roles y la tabla de usuarios
class UserHasRoles(models.Model):
    id_user = models.ForeignKey('users.User', on_delete=models.CASCADE, db_column="id_user")
    id_rol = models.ForeignKey("roles.Role", on_delete=models.CASCADE, db_column="id_rol")
    
    class Meta:
        db_table = 'user_has_roles'
        unique_together = ('id_user', 'id_rol')


class User(models.Model):
    id = models.AutoField(primary_key=True) #Así se crea una pk autoincremental
    name = models.CharField(max_length=255)
    lastname = models.CharField(max_length=255)
    email = models.EmailField(unique=True)
    phone = models.CharField(max_length=255)
    image = models.CharField(max_length=255, null=True, blank=False)
    password = models.CharField(max_length=255)
    notification_token = models.CharField(max_length=255, null=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    roles = models.ManyToManyField(
        'roles.Role',
        through='users.UserHasRoles',
        related_name='users'
    
    ) #Aquí se hace una relación de muchos a muchos hacia la tabla de roles por medio del nombre de la clase del modelo creado en la instancia de roles

    class Meta:
        db_table = 'users' #Esto va a servir para darle un nombre exacto a nuesta base de datos y no la cree como: "users_users