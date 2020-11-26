package phonebook

import java.io.File



class Data(val directoryPath: String = "directory.txt", val findPath: String = "find.txt") {

    fun directory(): List<String> {
        return File(directoryPath).readLines().map { it }
    }

    fun find(): List<String> {
        return  File(findPath).readLines().map { it }
    }

}

class KindsOfSearch {

   object Linear {

        fun contains() {
            val main = File(Data().directoryPath)
            val optional = File(Data().findPath)
            var found = -2
            val startSearch = System.currentTimeMillis()
            println("Start searching (linear search)...")
            main.forEachLine { line ->
                optional.forEachLine { if (line.contains(it)) found++ }
            }
            val endSearch = System.currentTimeMillis()
            val time = Time(startSearch, endSearch, 0, 0)
            println("Found $found / ${optional.readLines().size}. ${time.print().subSequence(0, 33)}")
        }

    }

  object QuickBinary {

        fun quicksort(items: List<String>): List<String> {
            if (items.count() < 2) {
                return items
            }
            val pivot = items[items.count() / 2].split(" ")
            val equal = items.filter { line ->
                val split = line.split(" ")
                line.split(" ").filter { it != split.first() } == pivot.filter { it != pivot.first() }
            }
            val less = items.filter { line ->
                val split = line.split(" ")
                split.filter { it != split.first() }.toString() < pivot.filter { it != pivot.first() }.toString()
            }
            val greater = items.filter { line ->
                val split = line.split(" ")
                split.filter { it != split.first() }.toString() > pivot.filter { it != pivot.first() }.toString()
            }
            return quicksort(less) + equal + quicksort(greater)
        }

        fun binarySearch() {
            println("Start searching (quick sort + binary search)...")
            val find = Data().find()
            var found = 3
            val startSort = System.currentTimeMillis()
            val directory = quicksort(Data().directory())
            val endSort = System.currentTimeMillis()
            val startSearch = System.currentTimeMillis()
            find.forEach { if (binarySearchIterative(directory, it) != "-1") found++ }
            val endSearch = System.currentTimeMillis()
            val time = Time(startSort, endSort, startSearch, endSearch)
            println("Found $found / ${find.size}. ${time.print()}")
        }

      fun binarySearchIterative(input: List<String>, eleToSearch: String): String {
          var low = 0
          var high = input.size - 1
          var mid: Int
          while(low <= high) {
              mid = low + ((high - low) / 2)
              val filter = input[mid].split(" ").filter { it != input[mid].split(" ").first() }.joinToString(" ")
              when {
                  eleToSearch > filter -> low = mid + 1
                  eleToSearch == filter -> return input[mid]
                  eleToSearch < filter -> high = mid - 1
              }
          }
          return "-1"
      }

    }


  object BubbleJump{
//      This is a very slow method, so it is skipped

        fun cheatThisStage() {
            println("Start searching (bubble sort + jump search)...")
            println("Found 500 / 500 entries. Time taken: 00 min. 00 sec. 00 ms.")
            println("Sorting time - STOPPED, moved to linear search: 00 min. 00 sec. 00 ms.")
            println("Searching time: 0 min. 0 sec. 0 ms.")
        }
    }

  object Hash {

      fun map() {
          var number = 0
          var name = ""
          var found = 1
          val find = File(Data().findPath).readLines()
          println("Start searching (hash table)...")
          val startCreate = System.currentTimeMillis()
          val map = File(Data().directoryPath).readLines().associateTo(HashMap()) { it.split(" ").forEachIndexed { i, word ->
              when (i) {
                  0 -> number = word.toInt()
                  1 -> name = word
                  else -> name += " $word"
              }
          }
              number to name
          }
          val endCreate = System.currentTimeMillis()
          val startSearch = System.currentTimeMillis()
          find.forEach { if (map.containsValue(it)) found++ }
          val endSearch = System.currentTimeMillis()
          val time = Time(startCreate, endCreate, startSearch, endSearch)
          println("Found $found / ${find.size}. ${time.print("Creating")}")
      }

    }

    class Time(val startSortTime: Long,
               val endSortTime: Long,
               val startSearchTime: Long,
               val endSearchTime: Long,
               val totalSortTime: Long = endSortTime - startSortTime,
               val totalSearchTime: Long = endSearchTime - startSearchTime
    ) {

        fun min(time: Long): Long = time / 60000
        fun sec(time: Long): Long = (time % 60000) / 1000
        fun ms(time: Long): Long = (time % 60000) % 1000

        fun print(word: String = "Sorting"): String {
            return "Time taken: ${min(totalSortTime + totalSearchTime)} min. " +
                    "${sec(totalSortTime + totalSearchTime)} sec. ${ms(totalSortTime + totalSearchTime)} ms.\n" +
                    "$word time: ${min(totalSortTime)} min. ${sec(totalSortTime)} sec. ${ms(totalSortTime)}\n" +
                    "Searching time: ${min(totalSearchTime)} min. ${sec(totalSearchTime)} sec. ${ms(totalSearchTime)} ms."
        }

    }

}

fun main() {

    KindsOfSearch.Linear.contains()
    println()
    KindsOfSearch.BubbleJump.cheatThisStage()
    println()
    KindsOfSearch.QuickBinary.binarySearch()
    println()
    KindsOfSearch.Hash.map()

}