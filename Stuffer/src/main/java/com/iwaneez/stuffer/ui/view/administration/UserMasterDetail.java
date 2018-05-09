package com.iwaneez.stuffer.ui.view.administration;

import com.iwaneez.stuffer.persistence.entity.User;
import com.iwaneez.stuffer.persistence.repository.UserRepository;
import com.iwaneez.stuffer.ui.component.masterdetail.MasterDetail;
import com.iwaneez.stuffer.util.ApplicationContextUtils;

public class UserMasterDetail extends MasterDetail<User, UserMasterComponent, UserDetailComponent> {

    private UserRepository userRepository;


    public UserMasterDetail() {
        super(new UserMasterComponent(), new UserDetailComponent());
        userRepository = ApplicationContextUtils.getApplicationContext().getBean(UserRepository.class);
    }

    @Override
    protected void deleteItem(User item) {
        userRepository.delete(item);
    }
}
