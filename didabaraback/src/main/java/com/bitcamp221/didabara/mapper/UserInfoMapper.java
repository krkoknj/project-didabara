package com.bitcamp221.didabara.mapper;

import com.bitcamp221.didabara.dto.UserAndUserInfoDTO;
import com.bitcamp221.didabara.dto.UserInfoDTO;
import com.bitcamp221.didabara.model.UserInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface UserInfoMapper {


  @Select("SELECT * FROM user_info WHERE id=#{id}")
  UserInfoDTO findByIdUserInfo(@Param("id") String id);

  @Update("UPDATE user_info SET file_name=#{svg} WHERE id=#{id}")
  int updateSvg(@Param("id") String id, @Param("svg") String svgName);


  @Results({
          @Result(property = "fileOriName", column = "file_ori_name"),
          @Result(property = "filename", column = "file_name"),
          @Result(property = "profileImageUrl", column = "profile_image_url")
  })
  @Select("SELECT file_ori_name,file_name,profile_image_url FROM user_info WHERE id=#{id}")
  UserInfoDTO findByIdInUserInfo(@Param("id") String id);


  @Select("SELECT * FROM user_info JOIN user ON user_info.id = user.id WHERE user.id=#{id}")
  Map<String, UserInfoDTO> findByMap(@Param("id") Long id);

  @Select("SELECT * FROM user_info JOIN user ON user_info.id = user.id WHERE user.id=#{id}")
  UserAndUserInfoDTO findByDTO(@Param("id") String id);

  @Update("UPDATE user_info " +
          "JOIN user " +
          "ON user_info.id = user.id " +
          "SET user.nickname = #{map.nickname}, " +
          "user.password = #{map.password}, " +
          "user_info.job = #{map.job}, " +
          "user_info.profile_image_url = #{map.profile_image_url}, " +
          "user_info.role = #{map.role} " +
          "WHERE user.id = #{id} ")
  int updateUserInfo(@Param("id") String id, @Param("map") Map map);

  @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
  int updateUserPassword(@Param("id") String id, @Param("password") String password) throws Exception;

  @Select("SELECT * FROM user WHERE nickname=#{map.nickname}")
  boolean checkNickname(@Param("map") Map map);

  @Delete("DELETE a,b FROM user_info a " +
          "LEFT JOIN user b " +
          "ON a.id = b.id " +
          "WHERE a.id = #{id}")
  int deleteUserAndInfo(@Param("id") String id);

  @Insert("INSERT INTO user_info VALUES(#{id},current_date(),current_date(),#{code})")
  int insert(@Param("user") UserInfoEntity userInfo);

  @Update("UPDATE user_info SET ban = #{user.ban} WHERE id = #{user.id}")
  UserInfoDTO updateBan(@Param("user") UserInfoDTO userInfo);

  @Select("SELECT * FROM user_info " +
          "JOIN user " +
          "ON user_info.id = user.id " +
          "WHERE user_info.id =#{id}")
  Map findByIdUserAndInfo(@Param("id") String id);

  @Select("SELECT * FROM user WHERE id=#{id}")
  UserAndUserInfoDTO findByIdUser(@Param("id") String id);

  @Update("UPDATE user_info " +
          "JOIN user " +
          "ON user_info.id = user.id " +
          "SET user.nickname = #{uid.nickname}, " +
          "user.password = #{uid.password}, " +
          "user_info.job = #{uid.job}, " +
          "user.username = #{uid.username}, " +
          "user.phone_number = #{uid.phoneNumber}, " +
          "user_info.modified_date = current_date() " +
          "WHERE user.id = #{id} ")
  int updateUserInfoDTO(@Param("id") String id, @Param("uid") UserAndUserInfoDTO uid);

  @Update("UPDATE user_info SET profile_image_url=#{user.profileImageUrl}, file_name=#{user.filename} WHERE id=#{user.id}")
  int updateImage(@Param("user") UserInfoEntity userInfoEntity);

  @Update("UPDATE user_info SET file_name='' WHERE id=#{id}")
  int updateEmpty(@Param("id") String id);
}