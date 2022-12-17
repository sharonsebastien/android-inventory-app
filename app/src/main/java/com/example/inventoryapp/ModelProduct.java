package com.example.inventoryapp;

public class ModelProduct {
    int _id;
    String _name;
    String _stockLeft;
    String _category;
    boolean _isAddedToCart;
    int _quantity;
    int _cartId;
    int _orderId;
    String _createdAt;
    String _status;
    String _userName;

    public ModelProduct() {}

    public ModelProduct(int id,String name,String stockLeft, String category) {
        this._id = id;
        this._name = name;
        this._stockLeft = stockLeft;
        this._category = category;
    }

    public ModelProduct(String name,String stockLeft, String category) {
        this._stockLeft = stockLeft;
        this._category = category;
        this._name = name;
    }

    public int getId() {return _id;}
    public void setId(int id) {this._id = id;}

    public String getName() {return this._name; }
    public void setName(String name) {this._name = name;}

    public String getStockLeft() {return this._stockLeft; }
    public void setStockLeft(String stockLeft) {this._stockLeft = stockLeft;}

    public String getCategory() {return this._category; }
    public void setCategory(String _category) {this._category = _category;}

    public void setIsAddedToCart(boolean isAddedToCart) {
        this._isAddedToCart = isAddedToCart;
    }
    public boolean getIsAddedToCart() {
        return _isAddedToCart;
    }

    public void setQuantity(int quantity) {
        this._quantity = quantity;
    }
    public int getQuantity() {
        return _quantity;
    }

    public void setCartId(int cartId) {
        this._cartId = cartId;
    }
    public int getCartId() {
        return _cartId;
    }

    public void setStatus(String status) {
        this._status = status;
    }
    public String getStatus() {
        return _status;
    }

    public void setCreatedAt(String createdAt) {
        this._createdAt = createdAt;
    }
    public String getCreatedAt() {
        return _createdAt;
    }

    public void setOrderId(int orderId) {
        this._orderId = orderId;
    }
    public int getOrderId() {
        return _orderId;
    }

    public void setUserName(String userName) {
        this._userName = userName;
    }
    public String getUserName() {
        return _userName;
    }
}
