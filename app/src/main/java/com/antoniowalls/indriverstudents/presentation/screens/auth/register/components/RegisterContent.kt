package com.antoniowalls.indriverstudents.presentation.screens.auth.register.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antoniowalls.indriverstudents.R
import com.antoniowalls.indriverstudents.presentation.components.DefaultButton
import com.antoniowalls.indriverstudents.presentation.components.DefaultOutlinedTextField

@Composable
fun RegisterContent(navHostController: NavHostController, paddingValues: PaddingValues) {
    var email by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var lastname by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF6D6ADC), Color(0xFF4F90FC))
                )
            )
            .padding(paddingValues)
    ) {
        //textos de la izquierda
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 27.sp,
                modifier = Modifier
                    .rotate(90f)
                    .padding(top = 10.dp)
                    .clickable{navHostController.popBackStack()}
            )
            Spacer(modifier = Modifier.height(150.dp))
            Text(
                text = "Registro",
                color = Color.White,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.rotate(90f)
                    .padding(top = 60.dp)

            )
            Spacer(modifier = Modifier.height(150.dp))
        }
        //contenido de registro
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 60.dp, bottom = 35.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF292C83), Color(0xFF4E79E3))
                    ),
                    shape = RoundedCornerShape(
                        topStart = 35.dp,
                        bottomStart = 35.dp
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 25.dp)
            ) {
                //Esta es la caja donde estará todo el contenido
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    //así es como se centra la imagen
                    Image(
                        modifier = Modifier
                            .size(170.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.car_white),
                        contentDescription = null,
                    )

                }
                //nombre
                DefaultOutlinedTextField(
                    modifier = Modifier
                        .padding(end = 25.dp),
                    value = name,
                    label = "Nombre",
                    icon = Icons.Outlined.Person,
                    onValueChange = {
                        name = it
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                //apellido
                DefaultOutlinedTextField(
                    modifier = Modifier
                        .padding(end = 25.dp),
                    value = lastname,
                    label = "Apellido",
                    icon = Icons.Outlined.Person,
                    onValueChange = {
                        lastname = it
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                //correo
                DefaultOutlinedTextField(
                    modifier = Modifier
                        .padding(end = 25.dp),
                    value = email,
                    label = "Correo",
                    icon = Icons.Outlined.Email,
                    keyboardType = KeyboardType.Email,
                    onValueChange = {
                        email = it
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                //telefono
                DefaultOutlinedTextField(
                    modifier = Modifier
                        .padding(end = 25.dp),
                    value = phone,
                    label = "Telefono",
                    icon = Icons.Outlined.Call,
                    keyboardType = KeyboardType.Phone,
                    onValueChange = {
                        phone  = it
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                //contraseña
                DefaultOutlinedTextField(
                    modifier = Modifier
                        .padding(end = 25.dp),
                    value = password,
                    label = "Contraseña",
                    icon = Icons.Outlined.Lock,
                    keyboardType = KeyboardType.Password,
                    onValueChange = {
                        password = it
                    },
                    hideText = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                //confirmar contraseña
                DefaultOutlinedTextField(
                    modifier = Modifier
                        .padding(end = 25.dp),
                    value = confirmPassword,
                    label = "Confirmar contraseña",
                    icon = Icons.Outlined.Lock,
                    keyboardType = KeyboardType.Password,
                    onValueChange = {
                        confirmPassword = it
                    },
                    hideText = true
                )
                Spacer(modifier = Modifier.weight(1f))
                DefaultButton(
                    modifier = Modifier,
                    text = "Crear cuenta",
                    onClick = { },
                )
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(
                        modifier = Modifier
                            .width(30.dp)
                            .height(1.dp)
                            .background(Color.White)
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 7.dp),
                        text = "O",
                        color = Color.White,
                        fontSize = 17.sp
                    )
                    Spacer(
                        modifier = Modifier
                            .width(30.dp)
                            .height(1.dp)
                            .background(Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "¿Ya tienes una cuenta?",
                        color = Color.White,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        modifier = Modifier.clickable { navHostController.popBackStack() },
                        text = "Inicia sesión",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))

            }

        }
    }
}