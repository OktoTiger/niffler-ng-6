package guru.qa.niffler.service;

import guru.qa.niffler.data.dao.SpendDao;
import guru.qa.niffler.data.dao.imp.SpendDaoJdbc;
import guru.qa.niffler.model.SpendJson;

public class SpendDbClient {
    private final SpendDao spendDao = new SpendDaoJdbc();
    public SpendJson createSpend(SpendJson spend){
        return this;
    }

}
