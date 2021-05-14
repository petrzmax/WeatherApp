package pl.arturpetrzak.controller;

public enum FetchDataResult {
    SUCCESS,
    FAILED_BY_REQUEST_SYNTAX,
    FAILED_BY_API_AUTHORIZATION,
    FAILED_BY_SERVER,
    FAILED_BY_UNEXPECTED_ERROR
}
