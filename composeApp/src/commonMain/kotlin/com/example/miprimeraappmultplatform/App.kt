package com.example.miprimeraappmultplatform

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.time.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview

// Importaciones de recursos generados
import miprimeraappmultplatform.composeapp.generated.resources.Res
import miprimeraappmultplatform.composeapp.generated.resources.jp
import miprimeraappmultplatform.composeapp.generated.resources.fr
import miprimeraappmultplatform.composeapp.generated.resources.mx
import miprimeraappmultplatform.composeapp.generated.resources.id
import miprimeraappmultplatform.composeapp.generated.resources.eg

data class Pais(val nombre: String, val zonaHoraria: TimeZone, val bandera: DrawableResource)

fun obtenerHoraActualEn(ubicacion: String, zona: TimeZone): String {

    fun LocalTime.formateada() =
        "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:${second.toString().padStart(2, '0')}"

    val instanteActual = Clock.System.now()
    val tiempoLocal = instanteActual.toLocalDateTime(zona).time

    return "La hora en $ubicacion es ${tiempoLocal.formateada()}"
}

fun listaDePaises() = listOf(
    Pais("Japón", TimeZone.of("UTC+09:00"), Res.drawable.jp),
    Pais("Francia", TimeZone.of("UTC+01:00"), Res.drawable.fr),
    Pais("México", TimeZone.of("UTC-06:00"), Res.drawable.mx),
    Pais("Indonesia", TimeZone.of("UTC+07:00"), Res.drawable.id),
    Pais("Egipto", TimeZone.of("UTC+02:00"), Res.drawable.eg),
)

@Composable
@Preview
fun App(paises: List<Pais> = listaDePaises()) {
    MaterialTheme {
        var mostrarMenuPaises by remember { mutableStateOf(false) }
        var textoHoraUbicacion by remember { mutableStateOf("No se ha seleccionado ubicación") }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = textoHoraUbicacion,
                style = TextStyle(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
            )

            // El Box actúa como ancla para que el menú sepa dónde desplegarse
            Box {
                Button(onClick = { mostrarMenuPaises = !mostrarMenuPaises }) {
                    Text("Seleccionar Ubicación")
                }

                DropdownMenu(
                    expanded = mostrarMenuPaises,
                    onDismissRequest = { mostrarMenuPaises = false }
                ) {
                    paises.forEach { (nombre, zona, bandera) ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(bandera),
                                        modifier = Modifier.size(24.dp).padding(end = 8.dp),
                                        contentDescription = nombre
                                    )
                                    Text(nombre)
                                }
                            },
                            onClick = {
                                textoHoraUbicacion = obtenerHoraActualEn(nombre, zona)
                                mostrarMenuPaises = false
                            }
                        )
                    }
                }
            }
        }
    }
}