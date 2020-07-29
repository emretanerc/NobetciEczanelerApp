package com.etcmobileapps.nobetcieczaneler.Api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RestInterface {
    @GET    Call<List<Repo>> eczaneListele(@Url String url);
}