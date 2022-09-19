package com.medical.backend.service

import com.medical.backend.model.BookAppointment
import com.medical.backend.repository.BookAppointmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class BookAppointmentService(
    @Autowired
    val bookAppointmentRepository: BookAppointmentRepository

) {


    fun addAppointment(bookAppointment: BookAppointment):Mono<BookAppointment>{
        return bookAppointmentRepository.save(bookAppointment)
    }


    fun updateAppointment(id:String,bookAppointment: BookAppointment):Mono<BookAppointment>{
        return bookAppointmentRepository.save(bookAppointment)
    }

    fun deleteById(id: String): Mono<Void> {
        return bookAppointmentRepository.deleteById(id)
    }

    fun updateAppointmentById(s: String, bookAppointment: BookAppointment) {

    }


    fun finadAllAppoinment(): Flux<BookAppointment> {
        return  bookAppointmentRepository.findAll()

    }

    fun findAllBookAppoinment() {
        TODO("Not yet implemented")
    }

    fun findAllAppointments() {
        TODO("Not yet implemented")
    }


}



