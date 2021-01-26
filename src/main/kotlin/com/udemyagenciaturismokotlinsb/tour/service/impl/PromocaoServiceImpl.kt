package com.udemyagenciaturismokotlinsb.tour.service.impl

import com.udemyagenciaturismokotlinsb.tour.model.Promocao
import com.udemyagenciaturismokotlinsb.tour.service.PromocaoService
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Component
class PromocaoServiceImpl : PromocaoService {
    companion object{
        val inicialPromocoes = arrayOf(
            Promocao(1,"Maravilhosa viagem a Cancun","Cancun",true,7,4999.99),
            Promocao(2,"Viagem Radical com Rapel e escalada","Nova Zelandia",false,12,12000.0),
            Promocao(3,"Viagem Espiritual","Thailancia",false,17,15000.0),
            Promocao(4,"Viagem com a Familia","Gramado",false,5,3500.33)
        )
    }
    var promocoes = ConcurrentHashMap<Long,Promocao>(inicialPromocoes.associateBy(Promocao::id))

    override fun create(promocao: Promocao) {
        promocoes[promocao.id] = promocao
    }

    override fun getById(id: Long): Promocao? = promocoes[id]

    override fun delete(id: Long) {
        promocoes.remove(id)
    }

    override fun update(id: Long, promocao: Promocao) {
        delete(id)
        create(promocao)
    }

    override fun searchByLocal(local: String): List<Promocao> = promocoes.filter {
            it.value.local.contains(local,true)
        }.map ( Map.Entry<Long,Promocao>::value ).toList()

}