from django.db import models

# Create your models here.
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