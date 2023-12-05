package me.paxana.adventofcode

import java.io.InputStream

fun InputStream.toList(): List<String> = this.bufferedReader().useLines { it.toList() }