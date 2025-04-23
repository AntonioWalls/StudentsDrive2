package com.antoniowalls.indriverstudents.presentation.screens.auth.login.components

import android.widget.Toast
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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.antoniowalls.indriverstudents.R
import com.antoniowalls.indriverstudents.presentation.components.DefaultButton
import com.antoniowalls.indriverstudents.presentation.components.DefaultTextField
import com.antoniowalls.indriverstudents.presentation.navigation.screen.auth.AuthScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.LoginViewModel

@Composable
fun LoginContent(navHostController: NavHostController, paddingValues: PaddingValues, vm: LoginViewModel = hiltViewModel()){
    val state = vm.state

    val context = LocalContext.current

    LaunchedEffect(key1 = vm.errorMessage){
        if (vm.errorMessage.isNotEmpty()){
            Toast.makeText(context, vm.errorMessage, Toast.LENGTH_LONG).show()
        }
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
        Column(
            //con esto vas a usar toda la altura disponible, centras los elemento de manera vertical y en la parte inicial de manera horizontal que sería a la izquierda
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .rotate(90f)
                    .padding(top = 10.dp)
                    .clickable { navHostController.navigate(route = AuthScreen.Login.route)}
            )
            //se usa dp para anchuras, alturas y todito lo que tiene que ver con dimensiones
            //sp para el tamaño de fuente
            Spacer(modifier = Modifier.height(150.dp))
            Text(
                text = "Registro",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .rotate(90f)
                    .padding(top = 30.dp)
                    .clickable { navHostController.navigate(route = AuthScreen.Register.route)}
            )
            Spacer(modifier = Modifier.height(250.dp))
        }
    }

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
            modifier = Modifier.statusBarsPadding().padding(start = 25.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Bienvenido!",
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "de nuevo...",
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Medium
            )
            Box(
                modifier = Modifier.fillMaxWidth().padding(end = 25.dp)
            ){
                Image(
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.car_white),
                    contentDescription = null,
                )
            }

            Text(
                text = "Log in",
                color = Color.White,
                fontSize = 27.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(50.dp))
            //textfield de correo electrónico
            DefaultTextField(
                modifier = Modifier,
                value = state.email,
                label = "Correo electrónico",
                icon = Icons.Outlined.Email,
                onValueChange = {
                    vm.onEmailInput(it) //así se obtiene lo que el usuario digita usando el vm
                },
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(20.dp))
            //textfield de contraseña
            DefaultTextField(
                modifier = Modifier,
                value = state.password,
                label = "Contraseña",
                icon = Icons.Outlined.Lock,
                onValueChange = {
                    vm.onPasswordInput(it)
                },
                keyboardType = KeyboardType.Password,
                hideText = true
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultButton(
                modifier = Modifier,
                text = "Iniciar sesión",
                onClick = {
                    vm.login()
                },
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(modifier = Modifier
                    .width(30.dp)
                    .height(1.dp)
                    .background(Color.White)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 7.dp),
                    text = "O",
                    color = Color.White,
                    fontSize = 17.sp,
                )
                Spacer(modifier = Modifier
                    .width(30.dp)
                    .height(1.dp)
                    .background(Color.White)
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "No tienes cuenta?",
                    color = Color.White,
                    fontSize = 17.sp,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    modifier = Modifier.clickable {navHostController.navigate(route = AuthScreen.Register.route)},
                    text = "registrate?",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier= Modifier.height(60.dp))
        }
    }
}