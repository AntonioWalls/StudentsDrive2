from rest_framework import serializers
from .models import User

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'name', 'lastname', 'email', 'phone', 'image', 'password', 'notification_token']

    def create(self, validated_data):
        user = User.objects.create(**validated_data) #toma un diccionario clave- valor por medio de validated_data\
        return user