package hangeureut.domain.photo.service;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiApiServiceImpl implements GeminiApiService {
    private static final Logger log = LogManager.getLogger(GeminiApiServiceImpl.class);
    private static final String GEMINI_MODEL = "gemini-2.5-flash";
    private Gson gson = new Gson();
    private HttpHeaders headers = new HttpHeaders();

    @Value("${cloud.google.gemini.api-key}")
    private String apiKey;

    public String generateTitle(List<String> colors, List<String> labels, String place) {
        String url = "https://generativelanguage.googleapis.com/v1/models/" + GEMINI_MODEL + ":generateContent?key=" + apiKey;
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSON 데이터 구성
        String jsonBody;
        if (place == null) {
            jsonBody =
                    "{\"contents\":[{\"parts\":[{\"text\":\"이 글은 음식 사진 정보다. " +
                            "label: " + joinStrings(labels) + ". " +

                            "사진의 제목을 작성하라. " +

                            "사진에서 보이는 재료와 형태를 기준으로 음식 종류를 판단하라. " +
                            "보이지 않는 음식이나 재료는 절대 생성하지 마라. " +
                            "특정할 수 없으면 형태 중심으로 일반화하여 표현하라. " +

                            "제목은 블로그 글의 제목처럼 자연스럽게 작성하라. " +
                            "단순 메뉴명 나열처럼 쓰지 말고, 사진 속 핵심 시각 요소가 드러나게 작성하라. " +

                            "음식의 질감, 재료 상태, 배치, 단면 등 눈에 보이는 특징을 반영하라. " +
                            "과장된 감상 표현이나 광고 문구는 금지한다. " +

                            "'맛있는', '눈길을 끄는', '감성적인' 등의 표현은 사용하지 마라. " +
                            "'이 사진은', '한 접시', '오늘의' 같은 메타 표현도 사용하지 마라. " +

                            "label 단어는 출력하지 마라. " +
                            "dominant color는 출력하지 마라. " +

                            "15~25자 이내의 자연스러운 제목으로 작성하라. " +
                            "반드시 하나만, 한 줄로 출력하라. " +
                            "설명 없이 제목만 출력하라.\"}]}]}";

        } else {
            jsonBody =
                    "{\"contents\":[{\"parts\":[{\"text\":\"이 글은 음식 사진 정보다. " +
                            "label: " + joinStrings(labels) + ". " +

                            "사진의 제목을 작성하라. " +

                            "사진에서 보이는 재료와 형태를 기준으로 음식 종류를 판단하라. " +
                            "보이지 않는 음식이나 재료는 절대 생성하지 마라. " +
                            "특정할 수 없으면 형태 중심으로 일반화하여 표현하라. " +

                            "제목은 블로그 글의 제목처럼 자연스럽게 작성하라. " +
                            "단순 메뉴명 나열처럼 쓰지 말고, 사진 속 핵심 시각 요소가 드러나게 작성하라. " +

                            "음식의 질감, 재료 상태, 배치, 단면 등 눈에 보이는 특징을 반영하라. " +
                            "과장된 감상 표현이나 광고 문구는 금지한다. " +

                            "'맛있는', '눈길을 끄는', '감성적인' 등의 표현은 사용하지 마라. " +
                            "'이 사진은', '한 접시', '오늘의' 같은 메타 표현도 사용하지 마라. " +

                            "label 단어는 출력하지 마라. " +
                            "dominant color는 출력하지 마라. " +

                            "15~25자 이내의 자연스러운 제목으로 작성하라. " +
                            "반드시 하나만, 한 줄로 출력하라. " +
                            "설명 없이 제목만 출력하라.\"}]}]}";
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return extractTextFromJson(responseEntity.getBody());
        } else {
            return "Error: " + responseEntity.getStatusCodeValue();
        }

    }

    public String generateComment(List<String> colors, List<String> labels, String place) {
        String url = "https://generativelanguage.googleapis.com/v1/models/" + GEMINI_MODEL + ":generateContent?key=" + apiKey;
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody;
        if (place == null) {
            jsonBody =
                    "{\"contents\":[{\"parts\":[{\"text\":\"이 글은 음식 사진 정보다. " +
                            "label: " + joinStrings(labels) + ". " +

                            "'~에서 흔히 볼 수 있다', '~에서 제공되는'과 같은 일반화 문장은 금지한다. " +
                            "블로그 글처럼 자연스럽게 작성하되, 감상이나 평가 없이 관찰 중심으로 작성하라. " +

                            "첫 문장은 음식의 핵심 형태가 바로 드러나도록 시작하라. " +
                            "'이 사진은', '이런 구성은', '해당 음식은'과 같은 메타 표현은 사용하지 마라. " +

                            "사진에서 보이는 재료를 기준으로 음식 종류를 판단하라. " +
                            "보이지 않는 음식이나 재료는 절대 생성하지 마라. " +
                            "음식을 특정할 수 없으면 형태 중심으로 일반화하여 표현하라. " +

                            "눈으로 보이는 요소만 사용하여 음식의 형태, 재료의 상태, 배치, 단면, 질감 등을 구체적으로 묘사하라. " +
                            "재료는 보이는 경우에만 구체적으로 쓰고, 불확실할 경우 형태 중심으로 표현하라. " +
                            "접시와 주변 요소도 간단히 포함하되 불필요한 나열은 피하라. " +

                            "'~에서 흔히 볼 수 있다', '~에서 제공되는'과 같은 일반화 문장은 금지한다. " +

                            "눈으로 확인 가능한 범위 내에서 질감이나 맛에 대한 간단한 추론은 허용한다. 단, 과도한 해석이나 원인 설명은 금지한다. " +
                            "'눈길을 끈다', '맛있어 보인다' 등의 표현도 금지한다. " +

                            "문장은 관찰을 이어가는 방식으로 자연스럽게 연결하되, 불필요하게 끊지 마라. " +
                            "문장마다 새로운 시각 정보를 추가하라. " +

                            "문장은 총 3~4문장으로 작성하라. " +
                            "'~이다' 반복을 피하라. " +

                            "마지막 문장에서만 간단한 맛 표현을 한 번 허용한다. " +

                            "라벨은 출력하지 마라. " +
                            "설명 없이 결과만 출력하라.\"}]}]}";
        } else {
            jsonBody =
                    "{\"contents\":[{\"parts\":[{\"text\":\"이 글은 음식 사진 정보다. " +
                            "label: " + joinStrings(labels) + ". " +

                            "'~에서 흔히 볼 수 있다', '~에서 제공되는'과 같은 일반화 문장은 금지한다. " +
                            "블로그 글처럼 자연스럽게 작성하되, 감상이나 평가 없이 관찰 중심으로 작성하라. " +

                            "첫 문장은 음식의 핵심 형태가 바로 드러나도록 시작하라. " +
                            "'이 사진은', '이런 구성은', '해당 음식은'과 같은 메타 표현은 사용하지 마라. " +

                            "사진에서 보이는 재료를 기준으로 음식 종류를 판단하라. " +
                            "보이지 않는 음식이나 재료는 절대 생성하지 마라. " +
                            "음식을 특정할 수 없으면 형태 중심으로 일반화하여 표현하라. " +

                            "눈으로 보이는 요소만 사용하여 음식의 형태, 재료의 상태, 배치, 단면, 질감 등을 구체적으로 묘사하라. " +
                            "재료는 보이는 경우에만 구체적으로 쓰고, 불확실할 경우 형태 중심으로 표현하라. " +
                            "접시와 주변 요소도 간단히 포함하되 불필요한 나열은 피하라. " +

                            "'~에서 흔히 볼 수 있다', '~에서 제공되는'과 같은 일반화 문장은 금지한다. " +

                            "눈으로 확인 가능한 범위 내에서 질감이나 맛에 대한 간단한 추론은 허용한다. 단, 과도한 해석이나 원인 설명은 금지한다. " +
                            "'눈길을 끈다', '맛있어 보인다' 등의 표현도 금지한다. " +

                            "문장은 관찰을 이어가는 방식으로 자연스럽게 연결하되, 불필요하게 끊지 마라. " +
                            "문장마다 새로운 시각 정보를 추가하라. " +

                            "문장은 총 3~4문장으로 작성하라. " +
                            "'~이다' 반복을 피하라. " +

                            "마지막 문장에서만 간단한 맛 표현을 한 번 허용한다. " +

                            "라벨은 출력하지 마라. " +
                            "설명 없이 결과만 출력하라.\"}]}]}";
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return extractTextFromJson(responseEntity.getBody());
        } else {
            return "Error: " + responseEntity.getStatusCodeValue();
        }
    }

    private String joinStrings(List<String> list) {
        //중간에는 쉼표, 마지막에는 마침표 넣도록하자.
        StringBuilder result = new StringBuilder();
        Iterator<String> iterator = list.iterator();

        //list가 빈 경우 예외처리 필요<
        if (iterator.hasNext()) {
            while (true) {
                result.append(iterator.next());
                if (!iterator.hasNext())
                    break;
                result.append(", ");
            }
        } else {

        }

        return result.toString();
    }

    private String extractTextFromJson(String jsonBody) {
        ResponseData responseData = gson.fromJson(jsonBody, ResponseData.class);

        return responseData.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    //앨범용, 사진용
    //결과 파싱?용

    public class ResponseData {
        private List<Candidate> candidates;
        private UsageMetadata usageMetadata;

        public List<Candidate> getCandidates() {
            return candidates;
        }

        public UsageMetadata getUsageMetadata() {
            return usageMetadata;
        }
    }

    class Candidate {
        private Content content;
        private String finishReason;
        private int index;
        private List<SafetyRating> safetyRatings;

        public Content getContent() {
            return content;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public int getIndex() {
            return index;
        }

        public List<SafetyRating> getSafetyRatings() {
            return safetyRatings;
        }
    }

    class Content {
        private List<Part> parts;
        private String role;

        public List<Part> getParts() {
            return parts;
        }

        public String getRole() {
            return role;
        }
    }

    class Part {
        private String text;

        public String getText() {
            return text;
        }
    }

    class SafetyRating {
        private String category;
        private String probability;

        public String getCategory() {
            return category;
        }

        public String getProbability() {
            return probability;
        }
    }

    class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;

        public int getPromptTokenCount() {
            return promptTokenCount;
        }

        public int getCandidatesTokenCount() {
            return candidatesTokenCount;
        }

        public int getTotalTokenCount() {
            return totalTokenCount;
        }
    }
}