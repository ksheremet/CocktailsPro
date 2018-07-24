package ch.sheremet.katarina.cocktailspro.model.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;

@Dao
public interface BeverageDetailsDao {
    @Query("SELECT * FROM beverage_detail_table WHERE id=:id")
    LiveData<BeverageDetails> getBeverageDetail(String id);

    @Insert
    void insert(BeverageDetails beverageDetails);

    @Delete
    void delete(BeverageDetails beverageDetails);
}
