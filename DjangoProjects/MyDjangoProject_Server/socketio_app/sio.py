import socketio
import json

sio = socketio.AsyncServer(async_mode='asgi', cors_allowed_origins="*")

#Evento de la conexión

@sio.event
async def connect(sid, environ):
    print(f'Cliente conectado: {sid}')
    await sio.emit('message', {'data': 'conexion exitosa'}, to = sid )

@sio.event
async def disconnect(sid):
    print(f'Cliente desconectado: {sid}')
    await sio.emit('driver_disconnected', { 'id_socket': sid })

@sio.event
async def message(sid, data):
    print(f'Datos del cliente en socket: {sid}: {data}')
    await sio.emit('new_message', data, to=sid)

@sio.event
async def change_driver_position(sid, data):
    try:
        if isinstance(data, dict):
            json_data = data
        elif isinstance(data, str):
            json_data = json.loads(data)
        else:
            print(f'Error en el tipo de dato: {type(data)}')
            return
        print(f'Emoitio nueva posicion en socket: {sid}: {data}')
        await sio.emit(
            'new_driver_position', 
            {
                'id_socket': sid,
                'id': json_data['id'],
                'lat': json_data['lat'],
                'lng':json_data['lng']
            }
        )
    except json.JSONDecodeError as e:
        print(f'Error al parsear JSON {e}')
    except KeyError:
        print(f'Falta algun dato en la petición {e}:')