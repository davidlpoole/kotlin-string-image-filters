import java.lang.StringBuilder

fun main() {
    photoshop()
}

fun photoshop() {
    val picture = getPicture()?.let { trimPicture(it) }
    val filter = chooseFilter()
    println(picture)
    println(picture?.let { applyFilter(it, filter) })
}

fun choosePicture(): String? {
    // ask the user to choose a picture by its name from list of pre-defined pictures
    val allPictures = allPictures()
    var pictureName: String
    do {
        println("Please, choose a picture")
        println("The possible options:")
        println(allPictures)
        pictureName = safeReadLine()
    } while (getPictureByName(pictureName) == null)

    return getPictureByName(pictureName)
}

fun getCustomPicture(): String {
    println("Please input a custom picture")
    return safeReadLine()
}

fun getPicture(): String? {
    // asks the user to choose a pre-defined picture or to input a custom picture
    println("Do you want to use a pre-defined picture or use a custom one?")
    println("Please, input 'yes' for a pre-defined image or 'no' for  custom one")
    do {
        when (safeReadLine()) {
        "yes" -> { return choosePicture() }
        "no" -> { return getCustomPicture() }
        else -> { println("Please, input 'yes' or 'no'") }
    }
    } while (true)
}

fun safeReadLine(): String {
    // get input from user, handle null input with an error
    return readlnOrNull() ?: error("Invalid input")
}

fun chooseFilter(): String {
    // ask user to choose filter, loop until valid selection is made
    println("Please, choose the filter: 'borders' or 'squared'")
    do {
        when (val input = safeReadLine()) {
            "borders", "squared" -> { return input }
            else -> {
                println("Please, input 'borders' or 'squared'")
            }
        }
    } while (true)
}

fun trimPicture(picture: String): String {
    // remove common indents
    return picture.trimIndent()
}

fun applyFilter(
    trimmedPicture: String,
    filterName: String
): String {
    return when (filterName) {
        "borders" -> { applyBordersFilter(trimmedPicture) }
        "squared" -> { applySquaredFilter(trimmedPicture) }
        else -> error("Unexpected filter name")
    }
}

fun applyBordersFilter(trimmedPicture: String): String {
    // adds a border to the image
    val lines = trimmedPicture.lines()
    val borderedPicture = StringBuilder()
    val width = getPictureWidth(trimmedPicture)
    borderedPicture.append(borderSymbol.toString().repeat(width + 4))
    borderedPicture.append(newLineSymbol)
    for (line in lines) {
        borderedPicture.append(borderSymbol)
        borderedPicture.append(separator)
        borderedPicture.append(line)
        borderedPicture.append(separator)
        val add = width - line.length // if some lines have different lengths
        if (add > 0) { borderedPicture.append(separator.toString().repeat(add)) }
        borderedPicture.append(borderSymbol)
        borderedPicture.append(newLineSymbol)
    }
    borderedPicture.append(borderSymbol.toString().repeat(width + 4))

    return borderedPicture.toString()
}

fun applySquaredFilter(trimmedPicture: String): String {
    // adds a border using the borders filter,
    // then duplicates it horizontally and vertically
    // so the output is 4 of the same image
    val borderedPicture = applyBordersFilter(trimmedPicture)
    val lines = borderedPicture.lines()
    val squaredPicture = StringBuilder()

    // duplicate image
    for (i in lines.indices) {
        squaredPicture.append(lines[i] + lines[i] + newLineSymbol)
    }

    // do the same again but ignore first line which is the horizontal border
    for (i in lines.indices) {
        if (i > 0) {
            squaredPicture.append(lines[i] + lines[i] + newLineSymbol)
        }
    }
    return squaredPicture.toString()
}