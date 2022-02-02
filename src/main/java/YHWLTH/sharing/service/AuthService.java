package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.LoginDTO;
import YHWLTH.sharing.dto.request.SignUpDTO;
import YHWLTH.sharing.dto.response.SignUpResponseDTO;
import YHWLTH.sharing.dto.response.TokenDTO;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.ex.AlreadyExistsEx;
import YHWLTH.sharing.ex.SignUpEx;
import YHWLTH.sharing.jwt.JwtFilter;
import YHWLTH.sharing.jwt.TokenProvider;
import YHWLTH.sharing.repo.UserRepo;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final StringRedisTemplate redisTemplate;

    public ResponseEntity<SignUpResponseDTO> signUp(SignUpDTO signUpDTO) throws AlreadyExistsEx {

        if (userRepo.findUserByUsername(signUpDTO.getUsername()).orElse(null) != null) {
            throw new AlreadyExistsEx("이미 존재하는 아이디입니다.");
        }
        if (!signUpDTO.getPassword().equals(signUpDTO.getPasswordConfirm())) {
            throw new SignUpEx("비밀번호가 일치하지 않습니다.", signUpDTO);
        }

        userRepo.save(new User(signUpDTO, passwordEncoder));

        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO(signUpDTO.getUsername());
        ApiUtil.makeSuccessResult(signUpResponseDTO, ApiUtil.SUCCESS_CREATED);

        return new ResponseEntity<>(signUpResponseDTO, HttpStatus.OK);
    }

    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {
        Authentication authentication = null;

        try {
            authentication = getAuthentication(loginDTO);
        } catch (BadCredentialsException ex) {
            log.info("[AuthService][login][BadCredentialsException]username: {}", loginDTO.getUsername());
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        TokenDTO tokenDTO = new TokenDTO(loginDTO.getUsername(), jwt);
        ApiUtil.makeSuccessResult(tokenDTO, ApiUtil.SUCCESS_OK);

        return new ResponseEntity<>(tokenDTO, httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<CommonResult> logout(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        Date expirationDate = tokenProvider.getExpirationDate(token);
        redisTemplate.opsForValue().set(token, "logout", expirationDate.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        CommonResult successResult = ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK);

        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    private Authentication getAuthentication(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
