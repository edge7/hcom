package com.ed7.user;

/*
    Object returned by users/{user_id}/totalvalue endpoint
 */
public class TotalValueUser extends UserInfo {
    private Long totalValue;

    public TotalValueUser() {}
    public TotalValueUser(Integer id) {
        super(id);
    }

    public Long getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }
}
