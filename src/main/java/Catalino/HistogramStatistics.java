package Catalino;

/**
 * Created by Dell_Pc on 5/6/2018.
 */
import Catalano.Core.IntRange;
public final class HistogramStatistics {

    /**
     * Don't let anyone instantiate this class.
     */
    private HistogramStatistics() {}

    /**
     * Calculate entropy value.
     * @param values Values.
     * @return Returns entropy value of the specified histogram array.
     */
    public static double Entropy( int[] values ){
        int     n = values.length;
        int     total = 0;
        double  entropy = 0;
        double  p;

        // calculate total amount of hits
        for ( int i = 0; i < n; i++ )
        {
            total += values[i];
        }

        if ( total != 0 )
        {
            // for all values
            for ( int i = 0; i < n; i++ )
            {
                // get item's probability
                p = (double) values[i] / total;
                // calculate entropy
                if ( p != 0 )
                    entropy += ( -p * (Math.log10(p)/Math.log10(2)) );
            }
        }
        return entropy;
    }

    /**
     * Get range around median containing specified percentage of values.
     * @param values Values.
     * @param percent Values percentage around median.
     * @return Returns the range which containes specifies percentage of values.
     */
    public static IntRange GetRange( int[] values, double percent ){
        int total = 0, n = values.length;

        // for all values
        for ( int i = 0; i < n; i++ )
        {
            // accumalate total
            total += values[i];
        }

        int min, max, hits;
        int h = (int) ( total * ( percent + ( 1 - percent ) / 2 ) );

        // get range min value
        for ( min = 0, hits = total; min < n; min++ )
        {
            hits -= values[min];
            if ( hits < h )
                break;
        }
        // get range max value
        for ( max = n - 1, hits = total; max >= 0; max-- )
        {
            hits -= values[max];
            if ( hits < h )
                break;
        }
        return new IntRange( min, max );
    }

    /**
     * Calculate Kurtosis value.
     * @param values Values.
     * @return Returns kurtosis value of the specified histogram array.
     */
    public static double Kurtosis(int[] values){
        double mean = Mean(values);
        double std = StdDev(values, mean);
        return Kurtosis(values, mean, std);
    }

    /**
     * Calculate Kurtosis value.
     * @param values Values.
     * @param mean Specified mean.
     * @param stdDeviation Specified standard deviation.
     * @return Returns kurtosis value of the specified histogram array.
     */
    public static double Kurtosis(int[] values, double mean, double stdDeviation){
        double n = 0;
        for (int i = 0; i < values.length; i++)
            n += values[i];

        double part1 = n * (n + 1);
        part1 /= ((n - 1) * (n - 2) * (n - 3));

        double part2 = 0;
        for (int i = 0; i < values.length; i++) {
            part2 += Math.pow((i - mean) / stdDeviation, 4) * values[i];
        }

        double part3 = 3 * Math.pow((n - 1), 2);
        part3 /= (n - 2) * (n - 3);

        return part1 * part2 - part3;
    }

    /**
     * Calculate Mean value.
     * @param values Values.
     * @return Mean.
     */
    public static double Mean( int[] values ) {
        int     hits;
        long    total = 0;
        double  mean = 0;

        // for all values
        for ( int i = 0, n = values.length; i < n; i++ ) {
            hits = values[i];
            // accumulate mean
            mean += i * hits;
            // accumalate total
            total += hits;
        }
        return ( total == 0 ) ? 0 : mean / total;
    }

    /**
     * Calculate Median value.
     * @param values Values.
     * @return Median.
     */
    public static int Median( int[] values ){
        int total = 0, n = values.length;

        // for all values
        for ( int i = 0; i < n; i++ )
        {
            // accumalate total
            total += values[i];
        }

        int halfTotal = total / 2;
        int median = 0, v = 0;

        // find median value
        for ( ; median < n; median++ )
        {
            v += values[median];
            if ( v >= halfTotal )
                break;
        }

        return median;
    }

    /**
     * Calculate Mode value.
     * @param values Values.
     * @return Returns mode value of the histogram array.
     */
    public static int Mode( int[] values ){
        int mode = 0, curMax = 0;

        for ( int i = 0, length = values.length; i < length; i++ )
        {
            if ( values[i] > curMax )
            {
                curMax = values[i];
                mode = i;
            }
        }
        return mode;
    }

    /**
     * Calculate Skewness value.
     * @param values Values.
     * @return Returns skewness value of the specified histogram array.
     */
    public static double Skewness(int[] values){
        double mean = Mean(values);
        double std = StdDev(values, mean);
        return Skewness(values, mean, std);
    }

    /**
     * Calculate Skewness value.
     * @param values Values.
     * @param mean Specified mean.
     * @param stdDeviation Specified standard deviation.
     * @return Returns skewness value of the specified histogram array.
     */
    public static double Skewness(int[] values, double mean, double stdDeviation){
        double n = 0;
        for (int i = 0; i < values.length; i++)
            n += values[i];

        double part1 = n / (n - 1) * (n - 2);

        double part2 = 0;
        for (int i = 0; i < values.length; i++) {
            part2 += Math.pow((i - mean) / stdDeviation, 3) * values[i];
        }

        return part1 * part2;
    }

    /**
     * Calculate standart deviation.
     * @param values Values.
     * @return Standart deviation.
     */
    public static double StdDev( int[] values ){
        return StdDev( values, Mean( values ) );
    }

    /**
     * Calculate standart deviation.
     * @param values Values.
     * @param mean Mean.
     * @return Standart deviation.
     */
    public static double StdDev( int[] values, double mean ){
        double  stddev = 0;
        double  diff;
        int     hits;
        int     total = 0;

        // for all values
        for ( int i = 0, n = values.length; i < n; i++ )
        {
            hits = values[i];
            diff = (double) i - mean;
            // accumulate std.dev.
            stddev += diff * diff * hits;
            // accumalate total
            total += hits;
        }

        return ( total == 0 ) ? 0 : Math.sqrt( stddev / (total - 1) );
    }
}
