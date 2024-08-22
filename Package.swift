// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorDeviceAuthenticity",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorDeviceAuthenticity",
            targets: ["DeviceAuthenticityPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "DeviceAuthenticityPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/DeviceAuthenticityPlugin"),
        .testTarget(
            name: "DeviceAuthenticityPluginTests",
            dependencies: ["DeviceAuthenticityPlugin"],
            path: "ios/Tests/DeviceAuthenticityPluginTests")
    ]
)