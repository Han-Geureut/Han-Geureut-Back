console.log("이름: 최미주, 학번: 2171434, 과목명: 정보보안(N), 과제명: 미니 인증 구현\n");
const crypto = require('crypto');
const readline = require('readline');

// 입력 받기 설정
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

// SHA-256 + salt + iteration
function hashPassword(password, salt, iterations) {
    let hash = password + salt;

    for (let i = 0; i < iterations; i++) {
        hash = crypto.createHash('sha256').update(hash).digest('hex');
    }

    return hash;
}

// 128비트 salt 생성
function generateSalt() {
    return crypto.randomBytes(16).toString('hex'); // 16 bytes = 128bit
}

// ===================== 실행 =====================

// 1. 사용자 등록
rl.question("등록 ID: ", (regId) => {
    rl.question("등록 비밀번호: ", (regPw) => {

        const salt = generateSalt();
        const iteration = 1000;
        const hash = hashPassword(regPw, salt, iteration);

        console.log("\n[사용자 등록 완료]");
        console.log("등록 ID:", regId);
        console.log("Salt:", salt);
        console.log("Iterations:", iteration);
        console.log("Hashcode:", hash);

        // 2. 사용자 인증
        rl.question("\n검증 ID 입력: ", (checkId) => {
            rl.question("검증 비밀번호 입력: ", (checkPw) => {

                const checkHash = hashPassword(checkPw, salt, iteration);

                if (regId === checkId && hash === checkHash) {
                    console.log("인증 성공!");
                } else {
                    console.log("인증 실패: 비밀번호가 일치하지 않습니다.");
                }

                console.log("검증 Hashcode:", checkHash);

                rl.close();
            });
        });
    });
});