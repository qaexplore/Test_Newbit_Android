package config;

import android.content.Context;

import com.spark.otcbitrade.data.DataRepository;
import com.spark.otcbitrade.data.LocalDataSource;
import com.spark.otcbitrade.data.RemoteDataSource;

/**
 * Created by Administrator on 2017/9/25.
 */

public class Injection {
    public static DataRepository provideTasksRepository(Context context) {
        return DataRepository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance(context));
    }
}
