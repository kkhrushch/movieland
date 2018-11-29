package com.khrushch.movieland.model.request;

import java.util.Objects;

public class SortingParam {
    private String columnName;
    private SortingOrder sortingOrder;

    public SortingParam(String columnName, SortingOrder sortingOrder) {
        this.columnName = columnName;
        this.sortingOrder = sortingOrder;
    }

    public String getColumnName() {
        return columnName;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortingParam that = (SortingParam) o;
        return Objects.equals(columnName.toLowerCase(), that.columnName.toLowerCase()) &&
                sortingOrder == that.sortingOrder;
    }

    @Override
    public String toString() {
        return columnName + " " + sortingOrder;
    }
}
