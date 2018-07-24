package ch.sheremet.katarina.cocktailspro.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.model.Beverage;

@Dao
public interface BeverageDao {
    @Query("SELECT * FROM beverage_table")
    List<Beverage> getAllBeverages();

    @Insert
    void insertBeverage(Beverage beverage);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateBeverage(Beverage beverage);

    @Delete
    void deleteBeverage(Beverage beverage);

    @Query("SELECT * FROM beverage_table WHERE id=:id")
    Beverage getBeverageById(String id);
}
