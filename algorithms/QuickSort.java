package algorithms;

import java.util.List;
import java.util.Comparator;
import java.util.Collections;


public final class QuickSort {
    private static <T> int partition(List<T> ls, int low, int high, final Comparator<T> comp) {
        T pivot = ls.get(high);

        int j = low-1;
        for (int i = low; i < high; ++i)
            if ( comp.compare( ls.get(i), pivot ) <= 0 )
                Collections.swap(ls, ++j, i);

        Collections.swap(ls, ++j, high);
        return j;
    }

    private static <T> void sort(final List<T> ls, int low, int high, final Comparator<T> comp) {
        if (low < high) {
            int pivot = partition(ls, low, high, comp);

            sort(ls, low, pivot-1, comp);
            sort(ls, pivot+1, high, comp);
        }
    }

    public static <T> void sort(List<T> ls, Comparator<T> comp) {
        sort(ls, 0, ls.size()-1, comp);
    }
}

