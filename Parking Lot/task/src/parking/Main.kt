package parking

import java.util.*

data class Car(val id: String, val color: String)

data class Spot(val position: Int, var parkedCar: Car? = null)

class ParkingLot(
        private var spots: MutableList<Spot>,
) {
    fun create(size: Int) {
        this.spots = MutableList(size) { Spot(it + 1) }
        println("Created a parking lot with $size spots.")
    }

    private fun isCreated(): Boolean {
        if (this.spots.size > 0) {
            return true
        }
        println("Sorry, a parking lot has not been created.")
        return false

    }

    fun park(id: String, color: String) {
        if (this.isCreated()) {
            val spot: Spot? = this.spots.firstOrNull { it.parkedCar == null }
            if (spot != null) {
                spot.parkedCar = Car(id, color)
                println("$color car parked in spot ${spot.position}.")
            } else {
                println("Sorry, the parking lot is full.")
            }
        }
    }

    fun leave(position: Int) {
        if (this.isCreated()) {
            if (position in 1..this.spots.size) {
                if (this.spots[position - 1].parkedCar == null) {
                    println("There is no car in spot $position")
                } else {
                    this.spots[position - 1].parkedCar = null
                    println("Spot $position is free.")
                }
            } else {
                println("This spot doesn't exist.")
            }
        }
    }

    fun status() {
        if (this.isCreated()) {
            val occupiedSpots: List<Spot> = this.spots.filter { it.parkedCar != null }
            if (occupiedSpots.isEmpty()) {
                println("Parking lot is empty.")
            } else {
                for (spot in occupiedSpots) {
                    println("${spot.position} ${spot.parkedCar!!.id} ${spot.parkedCar!!.color}")
                }
            }
        }
    }

    fun getRegByColor(color: String) {
        if (this.isCreated()) {
            val registrationIds: List<String> = this.spots.filter {
                it.parkedCar != null && it.parkedCar!!.color.uppercase() == color.uppercase()
            }.map { it.parkedCar!!.id }
            if (registrationIds.isEmpty()) {
                println("No cars with color $color were found.")
            } else {
                println(registrationIds.joinToString(", "))
            }
        }
    }

    fun getSpotByColor(color: String) {
        if (this.isCreated()) {
            val positions: List<Int> = this.spots.filter {
                it.parkedCar != null && it.parkedCar!!.color.uppercase() == color.uppercase()
            }.map { it.position }
            if (positions.isEmpty()) {
                println("No cars with color $color were found.")
            } else {
                println(positions.joinToString(", "))
            }
        }
    }

    fun getSpotByReg(id: String) {
        if (this.isCreated()) {
            val spot: Spot? = this.spots.find {
                it.parkedCar?.id == id
            }
            if (spot == null) {
                println("No cars with registration number $id were found.")
            } else {
                println(spot.position)
            }
        }
    }
}


fun main() {
    val parkingLot = ParkingLot(emptyList<Spot>().toMutableList())
    val scanner = Scanner(System.`in`)

    do {
        val input: String = scanner.nextLine()
        if (input.isNotEmpty()) {
            val args = input.split(" ")
            when (args[0]) {
                "park" -> parkingLot.park(args[1], args[2])
                "leave" -> parkingLot.leave(args[1].toInt())
                "create" -> parkingLot.create(args[1].toInt())
                "status" -> parkingLot.status()
                "reg_by_color" -> parkingLot.getRegByColor(args[1])
                "spot_by_color" -> parkingLot.getSpotByColor(args[1])
                "spot_by_reg" -> parkingLot.getSpotByReg(args[1])
                "exit" -> break
                else -> println("Invalid option")
            }
        }
    } while (input.isNotEmpty())
}

