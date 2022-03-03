import java.util.*
import kotlin.collections.ArrayList

class Karta(var znak : Char, var widac : Boolean, var id: Int, var odgadniete : Boolean)
{
    fun wyswietl()
    {
        print(" ")
        if(widac) {
            print(this.znak)
        }
        else
        {
            print(165.toChar())
        }
        print(" ")
    }
    fun czyTakieSame( karta:Karta) : Boolean
    {
        return (this.id != karta.id && this.znak == karta.znak);
    }
}

fun stworzTalie( znaki:Array<Char> ) : Array<Karta>
{
    val lista : MutableList<Karta> = ArrayList()
    var id : Int = 0;
    for (znak in znaki)
    {
        lista.add(Karta(znak, false, id, false))
        id+=1;
        lista.add(Karta(znak, false, id, false))
        id+=1;

    }
    return lista.toTypedArray()
}

class Memo(var karty : Array<Karta> )
{
    init {
        this.karty.shuffle();
    }
    fun pobierzIndex(x : Int ,y : Int) : Int
    {
        var indexKarty = 2 * y + x;
        return indexKarty;
    }
    fun odkryjKarte(x : Int ,y : Int)
    {
        var karta = this.karty.get(pobierzIndex(x,y))
        if(!karta.odgadniete) {
            karta.widac = true;
        }
    }

    fun wyswietlPlansze()
    {
        val index : Int = 0

        for (index in 0..1) {
            print("  "+index+ "x" )
        }
        println()
        for (index in 0..1) {
            print("+---")
        }
        println("+")
        var iteratorKart = this.karty.iterator()
        for (index in 0 until this.karty.size / 2) {
            print("|")
            iteratorKart.next().wyswietl()
            print(" ")
            iteratorKart.next().wyswietl()
            println("| $index"+"y")
            for (index in 0..1) {
                print("+---")
            }
            index.inc();
            println("+")
        }

    }
    fun sprawdzCzyOkdryteSaTakieSame() : Boolean
    {
        var wszystkieWidoczneKarty = karty.filter { it.widac }

        for(karta1 in wszystkieWidoczneKarty)
        {
            for(karta2 in wszystkieWidoczneKarty)
            {
                if(karta1.czyTakieSame(karta2))
                {
                    if(!karta1.odgadniete && !karta2.odgadniete)
                    {
                        karta1.odgadniete = true;
                        karta2.odgadniete = true;
                        return true;
                    }
                }
            }
        }
    return false;
    }

    fun zakryjWszystkieKarty()
    {
        var wszystkieWidoczneKarty = karty.filter { it.widac }
        if(wszystkieWidoczneKarty.size.mod(2) == 0) {
            karty.map {
                if (!it.odgadniete ) {
                    it.widac = false;
                }
            }

        }

    }
}

fun zleDane( x : Int, y : Int,  iloscKart : Int): Boolean {

    if(x<0 || x >=2 ) {
        println("x zle podany. $x")
        return false;
    }
    println("$iloscKart $x $y ")
     if(y < 0 || y >= iloscKart )
    {
        println("y zle podany. $y")
        return false;
    }

    return true;
}

fun main(args: Array<String>) {

    var memo = Memo(stworzTalie(arrayOf<Char>('k','#','$','%','@',133.toChar())));

    var punkty = 0
    while (true)
    {

        println("~!~!~! PUNKTY:$punkty ~!~!~!")
        memo.wyswietlPlansze()
        memo.zakryjWszystkieKarty()
        println("Podaj pole do odkrycia")
        val reader = Scanner(System.`in`)
        var x = reader.nextInt()
        var y = reader.nextInt()

        if(zleDane(x,y,memo.karty.size/2)) {
            println("Odkrywam ($x i $y)")

            memo.odkryjKarte(x, y)
            if(memo.sprawdzCzyOkdryteSaTakieSame())
            {
                punkty+=1;
            }
        }
        println("")
        println("")
        println("")
        println("")
        println("")
        println("")
        println("")
        println("")
        println("")
        println("")

    }
}