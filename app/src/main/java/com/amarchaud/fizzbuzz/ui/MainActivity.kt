package com.amarchaud.fizzbuzz.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amarchaud.fizzbuzz.ui.graph.enterTransition
import com.amarchaud.fizzbuzz.ui.graph.exitTransition
import com.amarchaud.fizzbuzz.ui.graph.popEnterTransition
import com.amarchaud.fizzbuzz.ui.graph.popExitTransition
import com.amarchaud.fizzbuzz.ui.screen.fields.FieldsComposable
import com.amarchaud.fizzbuzz.ui.screen.result.ResultComposable
import com.amarchaud.fizzbuzz.ui.theme.FizzBuzzTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    object RouteComposable {
        const val fieldsScreen = "fieldsScreen"
        const val resultScreen = "resultScreen"
    }

    object ResultExtra {
        const val integer1 = "integer1"
        const val integer2 = "integer2"
        const val text1 = "text1"
        const val text2 = "text2"
        const val limit = "limit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            FizzBuzzTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = RouteComposable.fieldsScreen,
                    enterTransition = { enterTransition },
                    exitTransition = { exitTransition },
                    popEnterTransition = { popEnterTransition },
                    popExitTransition = { popExitTransition }
                ) {
                    composable(
                        route = RouteComposable.fieldsScreen,
                    ) {

                        FieldsComposable(
                            onNextScreen = { int1, int2, t1, t2, lim ->
                                navController.navigate(
                                    route = RouteComposable.resultScreen +
                                            "/$int1" +
                                            "/$int2" +
                                            "/$t1" +
                                            "/$t2" +
                                            "/$lim"
                                )
                            }
                        )
                    }

                    composable(
                        route = RouteComposable.resultScreen +
                                "/{${ResultExtra.integer1}}" +
                                "/{${ResultExtra.integer2}}" +
                                "/{${ResultExtra.text1}}" +
                                "/{${ResultExtra.text2}}" +
                                "/{${ResultExtra.limit}}",
                        arguments = listOf(
                            navArgument(ResultExtra.integer1) {
                                type = NavType.IntType
                            },
                            navArgument(ResultExtra.integer2) {
                                type = NavType.IntType
                            },
                            navArgument(ResultExtra.text1) {
                                type = NavType.StringType
                            },
                            navArgument(ResultExtra.text2) {
                                type = NavType.StringType
                            },
                            navArgument(ResultExtra.limit) {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        ResultComposable()
                    }
                }
            }
        }
    }
}