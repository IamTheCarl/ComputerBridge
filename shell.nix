{ pkgs ? import <nixpkgs> { } }:
let
  rust-overlay = import (builtins.fetchTarball "https://github.com/oxalica/rust-overlay/archive/stable.tar.gz");
  pkgs = import <nixpkgs> { overlays = [ rust-overlay ]; };
  rust = pkgs.rust-bin.stable.latest.default.override {
    extensions = [
      "rust-src"
      "rust-analyzer"
      "rustfmt"
      "clippy"
    ];
    targets = [
      "x86_64-unknown-linux-gnu"
    ];
  };
  rust_platform = pkgs.makeRustPlatform {
    cargo = rust;
    rustc = rust;
  };
in
pkgs.mkShell {
  buildInputs = [
    rust
    rust_platform.bindgenHook
    pkgs.crate2nix
    pkgs.jdk8

    # Normally I am very against including an IDE into a development environment,
    # but seeing how much the Forge community is tied into this sucker, I don't
    # think I really have a choice. Sorry about the 20 minute download to bring
    # up this repo.
    pkgs.jetbrains.idea-community-src
  ];

  # Set environment variables
  shellHook = ''
    export OPENSSL_NO_VENDOR=1
  '';
}
