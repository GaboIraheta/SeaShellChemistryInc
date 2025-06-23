
@Composable
fun MolarMassScreen(navController: NavController) {
    val navigationBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(bottom = navigationBarHeight)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(8.dp)
                        .background(Color.Black)
                )
                Spacer(Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .background(MainBlue)
                        .height(8.dp)
                )
                Spacer(Modifier.height(30.dp))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(20.dp))
            Row {
                Spacer(Modifier.width(20.dp))
                AppGoBackButton(60.dp) {
                    navController.popBackStack()
                }
            }
        }

    }
}