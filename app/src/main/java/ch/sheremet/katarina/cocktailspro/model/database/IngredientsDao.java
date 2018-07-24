package ch.sheremet.katarina.cocktailspro.model.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.model.Ingredients;

@Dao
public interface IngredientsDao {
    @Query("SELECT * FROM ingredient_table WHERE beverage_id=:id")
    LiveData<List<Ingredients>> getAll(String id);

    @Query("DELETE FROM ingredient_table WHERE beverage_id=:id")
    void deleteAll(String id);
}
