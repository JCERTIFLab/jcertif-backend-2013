package objects.access;

import objects.checker.Checker;

public class BasicDBObj {

    private Checker checker;

    public BasicDBObj(){
        checker = null;
    }

    public BasicDBObj(Checker checker){
        this.setChecker(checker);
    }

    protected Checker getChecker() {
        return checker;
    }

    protected void setChecker(Checker checker) {
        this.checker = checker;
    }
}
