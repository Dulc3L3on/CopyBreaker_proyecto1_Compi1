/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Enums;

/**
 *
 * @author phily
 */
public enum SintaxError {
    FATAL_ERROR,
    PARSER_EXPECTED,
    JSON_BAD_GENERAL_STRUCT, 
    JSON_SECTION_DEFINED_PREVIOUSLY,
    JSON_BAD_SECTION_LIST_DEFINITION,
    JSON_EXPECTED_SECTION_NAME,    
    JSON_BAD_SCORE_SECTION_CONTENT_STRUCT,
    JSON_BAD_CLASS_SECTION_CONTENT_STRUCT,
    JSON_BAD_CLASS_LIST,
    JSON_BAD_VARIABLE_SECTION_CONTENT_STRUCT,
    JSON_BAD_VARIABLE_LIST,    
    JSON_BAD_VARIABLE_ATRIBUTE_CONTENT_DECLARATION_STRUCT,
    JSON_BAD_LIST_OF_VARIABLE_ATRIBS,
    JSON_BAD_METHOD_SECTION_CONTENT_STRUCT,
    JSON_BAD_METHOD_LIST,    
    JSON_BAD_METHOD_ATRIBUTE_CONTENT_DECLARATION_STRUCT,
    JSON_BAD_LIST_OF_METHOD_ATRIBS,
    JSON_BAD_COMMENT_SECTION_CONTENT_STRUCT,
    JSON_BAD_COMMENTS_LIST
}
