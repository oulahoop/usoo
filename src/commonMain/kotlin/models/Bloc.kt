package models

enum class Bloc {
    A,
    B,
    C,
    D;

    fun getXFromBloc(): Double {
        return when (this) {
            A -> 50.0
            B -> 200.0
            C -> 350.0
            D -> 500.0
            else -> throw Exception("Bloc not found")
        }
    }
}
