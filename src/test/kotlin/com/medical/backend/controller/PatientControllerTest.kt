package com.medical.backend.controller

import ch.qos.logback.core.net.server.Client
import com.medical.backend.model.BookAppointment
import org.junit.jupiter.api.Assertions.*



import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import com.medical.backend.service.PatientService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Mono

@WebFluxTest(PatientController::class)
@AutoConfigureWebTestClient

class PatientControllerTest {

    // need to mock the service layer responses

    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var patientService: PatientService

   /* @Autowired
    lateinit var  patientRepository:  PatientRepository*/
    @TestConfiguration
    class ControllerTestConfig{
        @Bean
        fun patientService() = mockk<PatientService>()

       /* @Bean
        fun patientRepository() = mockk<PatientRepository>()*/
    }
   @Test
   //arranging the data memebre
    fun `should return list of all the patient`() {
//Expectation
        val expectedResponse = mapOf(
            "patientId" to "1",
            "patientFirstName" to "Chaitu",
            "patientLastname" to "shah",
            "userName" to "chv",
            "mobileNumber" to "9325059460",
            "email" to "chv@gmail.com",
            "gender" to "female",
            "dob" to "30/03/1997"

        )
        val patient1 = Patient(
            "1", "Chaitu", "shah", "chv", "9325059460", "chv@gmail.com", "female", "30/03/1997",

        )


        every {
            patientService.addPatient(patient1)
        } returns Mono.just(patient1)

        val response = client.post()
            .uri("/patients/add")
            .bodyValue(patient1)
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResponse
        //response.blockLast() shouldBe expectedResult[1]

        verify(exactly = 1) {
            patientService.addPatient(patient1)
        }

    }


    @Test

    fun `should able to delete patient`(){

        val expectedResponse = mapOf(
            "patientId" to "1",
            "patientFirstName" to "Chaitu",
            "patientLastname" to "shah",
            "userName" to "chv",
            "mobileNumber" to "9325059460",
            "email" to "chv@gmail.com",
            "gender" to "female",
            "dob" to "30/03/1997"

        )
        val patient1 = Patient(
            "1", "Chaitu", "shah", "chv", "9325059460", "chv@gmail.com", "female", "30/03/1997",

            )



        every {
            patientService.deleteById("1")
        }returns Mono.empty()


        val response= client.delete()
            .uri("/patients/1")
            .exchange()// invoking the end point
            .expectStatus().is2xxSuccessful

        verify(exactly = 1){
            patientService.deleteById("1")
        }
    }
}

