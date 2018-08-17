package ch.sheremet.katarina.cocktailspro.di;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.utils.ApiManager;
import ch.sheremet.katarina.cocktailspro.utils.BeverageDetailsDeserializer;
import ch.sheremet.katarina.cocktailspro.utils.IBeveragesApi;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/";
    private String urlString;


    public ApiModule(final String apiKey) {
        this.urlString = BASE_URL + apiKey + "/";
    }

    @Singleton
    @Provides
    ApiManager apiManager(Retrofit retrofit) {
        return new ApiManager(retrofit.create(IBeveragesApi.class));
    }

    @Singleton
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(urlString)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Singleton
    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    public GsonConverterFactory gsonConverterFactory() {
        GsonBuilder gsonBuilder =
                new GsonBuilder().registerTypeAdapter(BeverageDetails.class, new BeverageDetailsDeserializer());
        return GsonConverterFactory.create(gsonBuilder.create());
    }
}
