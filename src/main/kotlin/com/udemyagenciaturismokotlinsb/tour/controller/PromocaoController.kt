package com.udemyagenciaturismokotlinsb.tour.controller

import com.udemyagenciaturismokotlinsb.tour.model.Promocao
import com.udemyagenciaturismokotlinsb.tour.model.RespostaJson
import com.udemyagenciaturismokotlinsb.tour.service.PromocaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/promocoes")
class PromocaoController() {
    @Autowired
    lateinit var promocaoService: PromocaoService

    @GetMapping("/{id}")
    fun getGetById(@PathVariable id: Long): ResponseEntity<Promocao?> {
        val promocao = this.promocaoService.getById(id)

        val status = if (promocao == null) HttpStatus.NOT_FOUND else HttpStatus.OK
        return ResponseEntity(promocao,status)
    }

    @PostMapping
    fun create(@RequestBody promocao: Promocao): ResponseEntity<RespostaJson>{
        this.promocaoService.create(promocao)
        val respostaJson = RespostaJson ("ok" , Date())
        return ResponseEntity(respostaJson,HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit>{
        var status = HttpStatus.NOT_FOUND
        if (this.promocaoService.getById(id) != null){
            status = HttpStatus.ACCEPTED
            this.promocaoService.delete(id)
        }
        return ResponseEntity(Unit,status)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody promocao: Promocao): ResponseEntity<Unit>{
        var status = HttpStatus.NOT_FOUND
        if (this.promocaoService.getById(id) != null){
            status = HttpStatus.ACCEPTED
            this.promocaoService.update(id,promocao)
        }
        return ResponseEntity(Unit,status)
    }

    @GetMapping
    fun getAll(@RequestParam(required = false,defaultValue = "") localFilter: String):ResponseEntity<List<Promocao>>{
        var status = HttpStatus.OK
        val listaPromocoes = this.promocaoService.searchByLocal(localFilter)
        if(listaPromocoes.size == 0){
            status = HttpStatus.NOT_FOUND
        }
        return ResponseEntity(listaPromocoes,status)
    }




}