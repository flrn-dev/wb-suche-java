package com.example.dictionary;

import java.util.Comparator;

public enum SortFunction {
    ALPHABETICAL_ASC(Comparator.comparing(WordEntry::getLemma, String.CASE_INSENSITIVE_ORDER)),
    ALPHABETICAL_DESC(Comparator.comparing(WordEntry::getLemma, String.CASE_INSENSITIVE_ORDER).reversed()),
    LENGTH_ASC(Comparator.comparingInt((WordEntry e) -> e.getLemma().length())),
    LENGTH_DESC(Comparator.comparingInt((WordEntry e) -> e.getLemma().length()).reversed());

    private final Comparator<WordEntry> comparator;

    SortFunction(Comparator<WordEntry> comparator) {
        this.comparator = comparator;
    }

    public Comparator<WordEntry> getComparator() {
        return comparator;
    }
}
