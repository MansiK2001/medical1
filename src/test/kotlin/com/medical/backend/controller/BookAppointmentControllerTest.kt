package com.medical.backend.controller

import org.junit.jupiter.api.Assertions.*




import ch.qos.logback.core.net.server.Client
import com.medical.backend.model.BookAppointment
import org.junit.jupiter.api.Assertions.*
import com.medical.backend.model.Patient
import com.medical.backend.service.BookAppointmentService
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
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@WebFluxTest(BookAppointmentController::class)
@AutoConfigureWebTestClient
class BookAppointmentControllerTest {

    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var bookAppointmentService: BookAppointmentService

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun bookAppoinmentService() = mockk<BookAppointmentService>()
    }

    @Test
     fun `should return list of all the bookAppoinment`() {

         val bookAppointment1 = BookAppointment( "1", "Rohit", "Dr.Shah", "mumbai", "riya@gmail.com", "9004907903",
             "20/09/2022","fever"
         )


         val expectedResponse =
             mapOf(
                 "patientId" to "1",
                 "patientName" to "Rohit",
                 "doctorName" to "Dr.Shah",
                 "address" to "mumbai",
                 "email" to "riya@gmail.com",
                 "mobileNumber" to "9004907903",
                 "dateofAppointment" to "20/09/2022",
                 "reason" to "fever"

             )


         every {
             bookAppointmentService.addAppointment(bookAppointment1)
         } returns  Mono.just(bookAppointment1)

         val response = client.post()
             .uri("/v1/bookAppointment")
             .bodyValue(bookAppointment1)
             .accept(MediaType.APPLICATION_JSON)
             .exchange() //invoking the end point
             .expectStatus().is2xxSuccessful
             .returnResult<Any>()
             .responseBody

         response.blockFirst() shouldBe expectedResponse
         //response.blockLast() shouldBe expectedResult[1]

         verify(exactly = 1) {
             bookAppointmentService.addAppointment(bookAppointment1)
         }

     }

   /* @Test

    fun  `should able to delete appointment by id`(){

        val expectedResponse = mapOf("patientId" to "1",
            "patientName" to "rohit",
            "doctorName" to "Dr.shah",
            "address" to "Mumbai",
            "email" to "rosh@gmail.com",
            "mobileNumber" to "9004907903",
            "dateofAppointment" to "17/04/2001",
            "reason" to "cold")

        val bookAppointment1 = BookAppointment("1", "rohit", "Dr.shah", "Mumbai",
            "rosh@gmail.com", "9004907903", "17/04/2001",
            "cold", )

        every {
            bookAppointmentService.deleteById("1")
        }returns Mono.empty()

        val response = client.delete()
            .uri("/appointments/1")
            .exchange()// invoking the end point
            .expectStatus().is2xxSuccessful

        verify(exactly = 1){
            bookAppointmentService.deleteById("1")
        }
    }
*/
   /* @Test

    fun `should able to update appointment`(){

        val bookAppointment1 = BookAppointment( "1", "Rohit", "Dr.Shah", "mumbai", "riya@gmail.com", "9004907903",
            "20/09/2022","fever"
        )


        val expectedResponse =
            mapOf(
                "patientId" to "1",
                "patientName" to "Rohit",
                "doctorName" to "Dr.Shah",
                "address" to "mumbai",
                "email" to "riya@gmail.com",
                "mobileNumber" to "9004907903",
                "dateofAppointment" to "20/09/2022",
                "reason" to "fever"

            )



        every {
            bookAppointmentService.updateAppointment("1",bookAppointment1)
        }returns Mono.just(bookAppointment1)


        val response= client.put()
            .uri("/updateAppointment/1")
            .bodyValue(bookAppointment1)
            .exchange()// invoking the end point
            .expectStatus().is2xxSuccessful

        verify(exactly = 1){
            bookAppointmentService.updateAppointment("1",bookAppointment1)
        }
    }
*/
}







