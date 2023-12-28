package mock;

import org.example.model.User;
import org.example.repository.dao.UserRepository;

import java.util.List;

public class UserRepositoryMock implements UserRepository {
    private List<User> users;

    public UserRepositoryMock(List<User> users) {
        this.users = users;
    }

    @Override
    public User save(User user) {
        users = List.of(user);
        return users.get(0);
    }

    @Override
    public User get(String nickname, String password) {
        for(User user: users){
            if (nickname.equals(user.getNickname())&&password.equals(user.getPassword())){
                return user;
            }
        }
        return User.builder().build();
    }

    @Override
    public boolean nicknameIsPresentInDB(String nickname) {
        for (User user:users){
            if (nickname.equals(user.getNickname()))
                return true;
        }
        return false;
    }


    @Override
    public void updateAmount(int id, int sum) {

    }

    @Override
    public void addGameToAccount(int userId, int gameId) {

    }
}
