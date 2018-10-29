package dz_spring3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageDAO extends GeneralDAO<Storage> {

    public Storage save(Storage storage){
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STORAGE_ VALUES(?, ?, ?, ?)")) {

            preparedStatement.setLong(1, storage.getId());
            preparedStatement.setString(2, storage.getFormatsSupported());
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(4, storage.getStorageSize());

            int res = preparedStatement.executeUpdate();

            System.out.println("save storage with id " + storage.getId() + " was finished with result " + res);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return storage;
    }

    public void delete(long id){

    }

    public Storage update(Storage storage){
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STORAGE_ SET FORMAT_SUPPORTED = ?, COUNTRY_STORAGE = ?, SIZE_STORAGE = ? WHERE STORAGE_ID = ?")) {

            preparedStatement.setString(1, storage.getFormatsSupported());
            preparedStatement.setString(2, storage.getStorageCountry());
            preparedStatement.setLong(3, storage.getStorageSize());
            preparedStatement.setLong(4, storage.getId());

            int res = preparedStatement.executeUpdate();

            System.out.println("update storage with id " + storage.getId() + " was finished with result " + res);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return storage;
    }

    public Storage findById(long id)throws Exception{

        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM STORAGE_ S, FILE_ F WHERE S.STORAGE_ID = F.STORAGE_ID_F AND S.STORAGE_ID = ?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Storage storage = new Storage();

            List<File> fileList = new ArrayList<>();

            while (resultSet.next()){
                storage = new Storage(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getLong(4));

                File file = new File(resultSet.getLong(5), resultSet.getLong(6),
                        resultSet.getString(7), resultSet.getString(8), resultSet.getLong(9));

                fileList.add(file);
            }

            File[] files = new File[fileList.size()];
            for (int i = 0; i != fileList.size() ; i++) {
                if (files[i] == null){
                    files[i] = fileList.get(i);
                }
            }

            storage.setFiles(Arrays.asList(files));

            return storage;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }
}
