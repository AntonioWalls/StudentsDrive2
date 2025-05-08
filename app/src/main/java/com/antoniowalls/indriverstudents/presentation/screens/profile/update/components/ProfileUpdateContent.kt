package com.antoniowalls.indriverstudents.presentation.screens.profile.update.components

import android.app.Activity
import android.content.Intent
import com.antoniowalls.indriverstudents.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.antoniowalls.indriverstudents.MainActivity
import com.antoniowalls.indriverstudents.presentation.components.DefaultIconButton
import com.antoniowalls.indriverstudents.presentation.components.DefaultTextField
import com.antoniowalls.indriverstudents.presentation.navigation.screen.profile.ProfileScreen
import com.antoniowalls.indriverstudents.presentation.screens.profile.info.ProfileInfoScreen
import com.antoniowalls.indriverstudents.presentation.screens.profile.info.ProfileInfoViewModel
import com.antoniowalls.indriverstudents.presentation.screens.profile.update.ProfileUpdateViewModel

@Composable
fun ProfileUpdateContent(navHostController: NavHostController, paddingValues: PaddingValues, vm: ProfileUpdateViewModel = hiltViewModel()) {

    val activity = LocalContext.current as? Activity
    val state = vm.state

    Box( modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ){ //toda esta caja engloba el tamaño completo de la pantalla
        Column {
            Box( //esta caja es la que va a tener el cuadro del fondo del perfil
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF292C83), Color(0xFF4E79E3))
                        )
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text="Perfil de usuario",
                    color = Color.White,
                    modifier = Modifier.padding(top = 20.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,

                )
            }
            Spacer(modifier = Modifier.weight(1f))
            //ahora vamos a crear los botones de editar perfil y cerrar sesión con unos componentes creados en la carpeta components
            DefaultIconButton(
                modifier = Modifier,
                title = "Actualizar usuario",
                imageVector = Icons.Default.Edit,
                tint = Color.White,
                onClick = {
                    vm.update()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        //Aquí se crea la tarjeta del perfil del usuario
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(horizontal = 35.dp, vertical = 100.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(115.dp)
                        .clip(CircleShape)
                ){

                    if (!vm.user?.image.isNullOrBlank()) { //No es nula ni vacia
                        AsyncImage(
                            model = vm.user?.image,
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Image(
                            painter = painterResource(id = R.drawable.user_image),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                DefaultTextField(
                    modifier = Modifier,
                    value = state.name,
                    label = "Nombre",
                    icon = Icons.Default.Person,
                    onValueChange = {
                        vm.onNameInput(it)
                    }
                )
                DefaultTextField(
                    modifier = Modifier,
                    value = state.lastname,
                    label = "Apellido",
                    icon = Icons.Outlined.Person,
                    onValueChange = {
                        vm.onLastNameInput(it)
                    }
                )
                DefaultTextField(
                    modifier = Modifier,
                    value = state.phone,
                    label = "Telefono",
                    icon = Icons.Default.Phone,
                    onValueChange = {
                        vm.onPhoneInput(it)
                    }
                )

            }
        }
    }

}