from django.db import models

class Role(models.Model): #Este nombre de clase sirve para poder relacionar las tablas
    id = models.CharField(
        max_length=36,
        primary_key= True,
        editable= True
    )
    name = models.CharField(max_length=36, unique=True)
    image = models.CharField(max_length=255)
    route = models.CharField(max_length=255)
    created_at = models.DateTimeField(auto_now_add = True)
    updated_at = models.DateTimeField(auto_now = True)
    
    class Meta: 
        db_table = 'roles'