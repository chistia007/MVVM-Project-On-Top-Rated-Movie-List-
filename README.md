# MVVM-Project-On-Top-Rated-Movie-List

This project is done using MVVM pattern, Retrofit2, Rest api, Live Data, Glide.


First you have to create json to pojo object from any website
Choose json as source type and gson as annotation style.
Then get the classes from the website and import json dependency in buld.Gradle(Module:App) : 
    implementation 'com.squareup.retrofit2:retrofit:2.7.2'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.2'

1.create classes named Result and MovieModel for Models package under Service package that you got extraxting from josn in the previous step.

```java
public class Result {
    public Boolean getAdult() {
        return adult;
    }
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
    public String getBackdropPath() {
        return backdropPath;
    }
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
//  ........................................
//  ........................................

}
```




2. then create api services and retrofiInstances classes in the network package.Then provide necessary code to call the api.
3. in the MovieRepository class, we will intialize our repository and fetch data using rest api at the same time according to our need.



```java
public class MovieRepository {
    private static Context mcontext;
    private MovieModel movieModel;
    private List<Result> mResult;
    private static  MovieRepository instance;
    public static MovieRepository getInstance(Context context){
        if (instance==null){
            mcontext=context;
            instance=new MovieRepository();
        }
        return instance;
    }

    public List<Result> getMovieList(){
        ApiServices apiServices= RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        Call<MovieModel> call= apiServices.getTopRatedMovieList();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                movieModel=response.body();
                mResult=movieModel.getResults();
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });

        return mResult;
    }

}
```


    
    
4. then we will add Mutablelive data in the same class.
```java
public class MovieRepository {
    private static Context mcontext;
    private MovieModel movieModel;
    private List<Result> mResult;
    private MutableLiveData mutableLiveData;
    private static  MovieRepository instance;
    public static MovieRepository getInstance(Context context){
        if (instance==null){
            mcontext=context;
            instance=new MovieRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Result>>  getTopRatedMovieLists(){
        if(mutableLiveData==null){
            mutableLiveData=new MutableLiveData();
        }
        
        ApiServices apiServices= RetrofitInstance.getRetrofitInstance().create(ApiServices.class);
        Call<MovieModel> call= apiServices.getTopRatedMovieList();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                movieModel=response.body();
                mResult=movieModel.getResults();
                mutableLiveData.postValue(mResult);
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}
```

    
    
    
5. fetch the live data in ViewModel class from repository. this MovieListViewModel class will hold data until the activity is finised. it will even preverse data in onResume() mode.
```java
public class MovieListViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        movieRepository= MovieRepository.getInstance(application);
    }
    public LiveData<List<Result>> getTopRatedMovieLists(){
        return movieRepository.getTopRatedMovieLists();
    }
}
```
    
    
    
6. Now we add an observer in Mainactivity class under View package. if any data in the api gets changed, it will change it in repository, and ViewModel will catch the changed data. And obser will keep observing, whenever any data gets changed in the vViewModel, observier will get notified and it will update the UI(REcyclerView In our case).
```java
      movieListViewModel.getTopRatedMovieLists().observe(this, new Observer<List<Result>>() {
    
            @Override
            public void onChanged(List<Result> results) {
    
                movieListAdapter=new MovieListAdapter(MainActivity.this,results);
                recyclerView.setAdapter(movieListAdapter);
    
            }
        });

    }
}
```
