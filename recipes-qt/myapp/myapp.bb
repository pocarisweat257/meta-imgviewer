SUMMARY = "Simple QT image viewer application"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://imageviewer.pro"
SRC_URI:append = " file://main.cpp"
SRC_URI:append = " file://myimage.png"
SRC_URI:append = " file://imageviewer.service"
SRC_URI:append = " file://99-myapp.preset"
SRC_URI:append = " file://10-static.network"

S = "${WORKDIR}"

DEPENDS += "qtbase"
inherit qmake5 systemd

SYSTEMD_SERVICE_${PN} = "imageviewer.service"
SYSTEMD_AUTO_ENABLE = "enable"

QMAKESPEC = "linux-g++"

do_install:append() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/myapp ${D}${bindir}
    install -m 0644 ${WORKDIR}/myimage.png ${D}${bindir}
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/imageviewer.service ${D}${systemd_unitdir}/system/imageviewer.service
    install -d ${D}/${systemd_unitdir}/system-preset
    install -m 0644 ${WORKDIR}/99-myapp.preset ${D}${systemd_unitdir}/system-preset/99-myapp.preset
    install -d ${D}/etc/systemd/network
    install -m 0644 ${WORKDIR}/10-static.network ${D}/etc/systemd/network/10-static.network
}

FILESEXTRAPATHS:prepend := "${THISDIR}/source:"
FILES:${PN} += "${bindir}/myapp"
FILES:${PN} += "${systemd_unitdir}/system/imageviewer.service"
FILES:${PN} += "${systemd_unitdir}/system-preset/99-myapp.preset"
FILES:${PN} += "/etc/systemd/network/10-static.network"
