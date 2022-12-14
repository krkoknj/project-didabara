package com.bitcamp221.didabara.controller;


import com.bitcamp221.didabara.dto.ResponseDTO;
import com.bitcamp221.didabara.dto.UserDTO;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.security.TokenProvider;
import com.bitcamp221.didabara.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class UserController {

  private final UserService userService;
  private final TokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  //  회원가입
//  http://localhost:8080/auth/signup
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
      if (userDTO == null || userDTO.getPassword() == null) {
        throw new RuntimeException("Invalid Password value");
      }

      UserDTO registeredUser = userService.creat(userDTO.toEntity());

      log.info("회원가입 완료");

      return ResponseEntity.ok().body(registeredUser);

    } catch (Exception e) {
      ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }

  }

  //  로그인
  @PostMapping(value = "/signin")
  public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) throws Exception {
    UserEntity user = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder);


    // 계정문제로 주석 처리.
      /*EmailConfigEntity byId = emailConfigRepository.findById(user.getId())
              .orElseThrow(() -> new RuntimeException("인증 필요한 유저"));*/


    //    토큰 생성.
    final String token = tokenProvider.create(user);

    log.info("usertoken={}", token);


    final UserDTO responseUserDTO = UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .nickname(user.getNickname())
            .token(token)
            .build();

    return ResponseEntity.ok().body(responseUserDTO);
  }


  //조회
  // url로 접근할떄 토큰을 확인한다던가 보안성 로직이 필요할듯함?
  @GetMapping("/user/{id}")
  public UserEntity findbyId(@PathVariable Long id) {
    return userService.findById(id);
  }

  //수정
  //patch --> 엔티티의 일부만 업데이트하고싶을때
  //put --> 엔티티의 전체를 변경할떄
  //put 을 사용하면 전달한값 외는 모두 null or 초기값으로 처리된다고함..
  @PatchMapping("/user")
  public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
    try {
      UserEntity userEntity = UserEntity.builder()
              .id(userDTO.getId())
              .nickname(userDTO.getNickname())
              .password(passwordEncoder.encode(userDTO.getPassword()))
              .phoneNumber(userDTO.getPhoneNumber())
              .build();

      UserEntity updatedUser = userService.update(userEntity);

      UserDTO ResponseUserDTO = UserDTO.builder()
              .id(updatedUser.getId())
              .nickname(updatedUser.getNickname())
              .build();
      log.info("업데이트 완료");

      return ResponseEntity.ok().body(ResponseUserDTO);
    } catch (Exception e) {
      ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      log.error("업데이트 실패");
      return ResponseEntity.badRequest().body(responseDTO);
    }

  }

  //삭제
  @DeleteMapping("/user")
  public ResponseEntity<?> deletUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal String userId) {
    System.out.println("userDTO = " + userDTO.toString());
    System.out.println("userId = " + userId);

    boolean checkPwd = userService.checkPwd(userDTO, userId);

    if (!checkPwd) {
      return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다");
    } else {
      userService.deleteUser(Long.valueOf(userId));
      log.info("삭제완료");
      return ResponseEntity.ok().body("삭제 되었습니다.");
    }


  }


  //프론트에서 인가코드 받아오는 url
  /* 카카오 로그인 */
  @GetMapping("/kakao")
  public UserDTO kakaoCallback(@Param("code") String code) throws IOException {
    log.info("code={}", code);

    String[] access_Token = userService.getKaKaoAccessToken(code);
    String access_found_in_token = access_Token[0];
    // 배열로 받은 토큰들의 accsess_token만 createKaKaoUser 메서드로 전달

    return userService.createKakaoUser(access_found_in_token);
  }

  // https://kauth.kakao.com/oauth/authorize?client_id=4af7c95054f7e1d31cff647965678936&redirect_uri=http://localhost:8080/auth/kakao&response_type=code


}