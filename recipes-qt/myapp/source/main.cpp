#include <QApplication>
#include <QLabel>
#include <QPixmap>
#include <QWidget>
#include <QVBoxLayout>
#include <QPushButton>
#include <QScreen>
#include <QTimer>

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);

    // 메인 창과 레이아웃 생성
    QWidget window;
    QVBoxLayout *layout = new QVBoxLayout(&window);

    // 이미지 뷰어 (QLabel) 생성 및 이미지 로드
    QLabel *label = new QLabel;
    QPixmap pixmap("/usr/bin/myimage.png");
    if (pixmap.isNull()) {
        label->setText("Can't load image");
    } else {
        label->setPixmap(pixmap);
    }
    layout->addWidget(label);

    // 종료 버튼 추가
    QPushButton *exitButton = new QPushButton("종료 및 화면 클리어");
    layout->addWidget(exitButton);

    // 버튼 클릭 시 화면 클리어 후 종료하는 람다 함수 연결
    QObject::connect(exitButton, &QPushButton::clicked, [&](){
        // 1. 전체 화면을 덮는 검은색 위젯 생성
        QWidget clearWidget;
        clearWidget.setStyleSheet("background-color: black;");
        clearWidget.showFullScreen();

        // 2. 즉시 이벤트 처리해서 위젯을 화면에 그리기
        app.processEvents();

        // 3. 잠시 대기 후 애플리케이션 종료 (예: 100ms)
        QTimer::singleShot(100, &app, &QApplication::quit);
    });

    window.show();
    return app.exec();
}

