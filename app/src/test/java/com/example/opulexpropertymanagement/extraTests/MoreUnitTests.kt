package com.example.opulexpropertymanagement.extraTests

import com.example.opulexpropertymanagement.layers.data_layer.RegisterRepo
import com.example.opulexpropertymanagement.layers.z_ui.User
import com.example.tmcommonkotlin.InputValidation
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.AnyOf.anyOf
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MoreUnitTests {
    @Mock
    lateinit var registerRepo: RegisterRepo
    @Test
    fun inputValidation1() {
        val x = InputValidation.asCardNumber("wertwertwert")
        Assert.assertEquals(InputValidation.Result.Error("Must not contain letters"), x)
    }
    @Test
    fun doesMyStringHaveAValidValue() {
        val myString = "value2"
        assertThat(myString, anyOf(containsString("value1"), containsString("value2")))
    }
    @Test
    fun doAnotherAnotherTest() {
        val x = User(
            appapikey = "gsdfgsdfgsdfgweergsdfg456",
            msg = "??",
            email = "Bob@gmail.com",
            id =  "463",
            usertype = "landlord"
        )
//        Truth.assertThat(x)
    }
    @Before
    fun doAMockitoPreparation() {
        registerRepo = RegisterRepo()
        // setup threading

//        val immediate: Scheduler = object : Scheduler() {
//            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
//                // this prevents StackOverflowErrors when scheduling with a delay
//                return super.scheduleDirect(run, 0, unit)
//            }
//
//            override fun createWorker(): ExecutorWorker {
//                return ExecutorWorker(Executor { obj: Runnable -> obj.run() })
//            }
//        }
//
//        RxJavaPlugins.setInitIoSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
//        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
//        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
//        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
    }
    @Test
    fun doAMockitoTest() {
//        Mockito.doReturn(
//            {
//                registerRepo.streamTryRegisterResult.value =
//                    RegisterResult.Failure.EmailAlreadyExists
//            }()
//        )
//            .`when`(registerRepo.tryRegister(Mockito.anyString(),Mockito.anyString(),UserType.Tenant))
//              ^Exception in thread "main" java.lang.IllegalStateException: Module with the Main dispatcher had failed to initialize. For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used
//        assertEquals(RegisterResult.Failure.EmailAlreadyExists, registerRepo.streamTryRegisterResult.value)
//        Mockito.doReturn("""email already exists""")
//            .when(registerRepo.tryRegister(Mockito.anyString(),Mockito.anyString(),UserType.Tenant)) {
//
//            }

//        Assert.assertEquals("""email already exists""", registerRepo.tryRegister(Mockito.anyString(),Mockito.anyString(),UserType.Tenant))
//        val z = registerRepo.tryRegister("2@gmail.com","123", UserType.Tenant)
//        assertEquals("""email already exists""", z)
    }
    @After
    fun doAMockitoAfter() {

    }

}