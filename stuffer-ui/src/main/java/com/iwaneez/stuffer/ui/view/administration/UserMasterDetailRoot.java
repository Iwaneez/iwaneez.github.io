package com.iwaneez.stuffer.ui.view.administration;

import com.iwaneez.stuffer.persistence.entity.User;
import com.iwaneez.stuffer.persistence.repository.UserRepository;
import com.iwaneez.stuffer.ui.component.masterdetail.MasterDetailRoot;
import com.iwaneez.stuffer.util.ApplicationContextUtils;

public class UserMasterDetailRoot extends MasterDetailRoot<User, UserMaster, UserDetail> {

    private UserRepository userRepository;


    public UserMasterDetailRoot() {
        super(new UserMaster(), new UserDetail());
        userRepository = ApplicationContextUtils.getApplicationContext().getBean(UserRepository.class);
    }

    @Override
    protected void deleteItem(User item) {
        userRepository.delete(item);
    }
}
