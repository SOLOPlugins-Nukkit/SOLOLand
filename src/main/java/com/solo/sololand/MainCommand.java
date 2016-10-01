package com.solo.sololand;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import com.solo.sololand.util.Message;

class MainCommand{

  private Main plugin;

  public static final int HELP_LAND = 0;
  public static final int HELP_WORLD = 1;
  public static final int HELP_ISLAND = 2;
  public static final int HELP_BENCHMARK = 3;

  public MainCommand(Main plugin){
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command command, String[] args){
    if(!(sender instanceof Player)){
      sender.sendMessage("인게임에서만 사용가능합니다.");
      return true;
    } 
    Player player = (Player) sender;
    switch(command.getName().toLowerCase()){
      case "땅":
        if(args.length < 1){
          this.sendHelp(player, MainCommand.HELP_LAND, 1);
          return true;
        }
        switch(args[0]){
          case "생성":
          case "만들기":
            com.solo.sololand.command.land.Create.execute(player, args);
            break;
          case "삭제":
          case "제거":
            com.solo.sololand.command.land.Remove.execute(player, args);
            break;
          case "이동":
          case "워프":
            com.solo.sololand.command.land.Move.execute(player, args);
            break;
          case "정보":
            com.solo.sololand.command.land.Info.execute(player, args);
            break;
          case "목록":
          case "리스트":
          case "내땅":
            com.solo.sololand.command.land.List.execute(player, args);
            break;
          case "구매":
            com.solo.sololand.command.land.Buy.execute(player, args);
            break;
          case "매물":
          case "매물목록":
          case "판매목록":
          case "판매리스트":
            com.solo.sololand.command.land.SellList.execute(player, args);
            break;
          case "환영말":
          case "환영메세지":
          case "인삿말":
            com.solo.sololand.command.land.WelcomeMessage.execute(player, args);
            break;
          case "환영효과":
          case "환영파티클":
            com.solo.sololand.command.land.WelcomeParticle.execute(player, args);
            break;
          case "공유":
          case "같이쓰기":
          case "초대":
            com.solo.sololand.command.land.Share.execute(player, args);
            break;
          case "공유취소":
          case "같이쓰기취소":
          case "초대취소":
          case "추방":
            com.solo.sololand.command.land.CancelShare.execute(player, args);
            break;
          case "출입금지":
          case "출입":
          case "접근거부":
          case "접근금지":
          case "접근":
            com.solo.sololand.command.land.Access.execute(player, args);
            break;
          case "pvp":
          case "싸움":
          case "전투":
          case "pvp허용":
          case "전투허용":
          case "싸움허용":
          case "유저간전투허용":
          case "유저간싸움허용":
            com.solo.sololand.command.land.AllowFight.execute(player, args);
            break;
          case "아이템드랍":
          case "아이템드롭":
          case "드랍":
          case "아이템드랍허용":
          case "아이템드롭허용":
          case "아이템줍기":
          case "아이템줍기허용":
            com.solo.sololand.command.land.AllowPickUpItem.execute(player, args);
            break;
          case "스폰":
          case "스폰위치":
          case "스폰설정":
            com.solo.sololand.command.land.SetSpawn.execute(player, args);
            break;
          case "판매":
          case "팔기":
            com.solo.sololand.command.land.Sell.execute(player, args);
            break;
          case "판매취소":
            com.solo.sololand.command.land.CancelSell.execute(player, args);
            break;
          case "양도":
          case "주기":
            com.solo.sololand.command.land.Give.execute(player, args);
            break;
          case "크기변경":
          case "리사이즈":
          case "확장":
            com.solo.sololand.command.land.Resize.execute(player, args);
            break;
          case "방문자":
          case "방문유저":
            com.solo.sololand.command.land.Visitor.execute(player, args);
            break;
          case "공유목록":
          case "공유땅":
          case "공유된땅":
            com.solo.sololand.command.land.ShareList.execute(player, args);
            break;
          case "취소":
          case "작업취소":
          case "중단":
            com.solo.sololand.command.land.Cancel.execute(player, args);
            break;
          default:
            int page;
            try{
              page = Integer.parseInt(args[0]);
            }catch(Exception e){
              this.sendHelp(player, MainCommand.HELP_LAND, 1);
              break;
            }
            this.sendHelp(player, MainCommand.HELP_LAND, page);
        }
        return true;

      case "월드":
        if(args.length < 1){
          this.sendHelp(player, MainCommand.HELP_WORLD, 1);
          return true;
        }
        switch(args[0]){
          case "이름변경":
          case "이름":
          case "이름설정":
            com.solo.sololand.command.world.SetCustomName.execute(player, args);
            break;
          case "보호":
          case "블럭파괴":
            com.solo.sololand.command.world.Protect.execute(player, args);
            break;
          case "정보":
            com.solo.sololand.command.world.Info.execute(player, args);
            break;
          case "설정정보":
            com.solo.sololand.command.world.SettingInfo.execute(player, args);
            break;
          case "유저정보":
            com.solo.sololand.command.world.UserInfo.execute(player, args);
            break;
          case "땅정보":
            com.solo.sololand.command.world.LandInfo.execute(player, args);
            break;
          case "인벤세이브":
            com.solo.sololand.command.world.InvenSave.execute(player, args);
            break;
          case "전투허용":
          case "전투":
          case "pvp":
          case "싸움":
          case "싸움허용":
          case "유저간전투허용":
          case "유저간싸움허용":
            com.solo.sololand.command.world.AllowFight.execute(player, args);
            break;
          case "블럭당가격":
          case "땅블럭당가격":
            com.solo.sololand.command.world.PricePerBlock.execute(player, args);
            break;
          case "땅최대개수":
          case "땅최대갯수":
            com.solo.sololand.command.world.MaxLandCount.execute(player, args);
            break;
          case "땅최소길이":

            com.solo.sololand.command.world.MinLandLength.execute(player, args);
            break;
          case "땅최대길이":
            com.solo.sololand.command.world.MaxLandLength.execute(player, args);
            break;
          case "기본땅값":
          case "기본땅가격":
            com.solo.sololand.command.world.DefaultLandPrice.execute(player, args);
            break;
          case "땅생성허용":
          case "땅생성":
            com.solo.sololand.command.world.AllowCreateLand.execute(player, args);
            break;
          case "폭발허용":
          case "폭발":
            com.solo.sololand.command.world.AllowExplosion.execute(player, args);
            break;
          case "불번짐허용":
          case "불번짐":
            com.solo.sololand.command.world.AllowBurn.execute(player, args);
            break;
          default:
            int page;
            try{
              page = Integer.parseInt(args[0]);
            }catch(Exception e){
              this.sendHelp(player, MainCommand.HELP_WORLD, 1);
              break;
            }
            this.sendHelp(player, MainCommand.HELP_WORLD, page);
        }
        return true;

      case "섬":
        if(args.length < 1){
          this.sendHelp(player, MainCommand.HELP_ISLAND, 1);
          return true;
        }
        switch(args[0]){
          case "시작":
          case "월드생성":
            com.solo.sololand.command.island.Create.execute(player, args);
            break;
          case "구매":
          case "구입":
            com.solo.sololand.command.island.Buy.execute(player, args);
            break;
          case "이동":
            com.solo.sololand.command.island.Move.execute(player, args);
            break;
          case "목록":
          case "리스트":
            com.solo.sololand.command.island.List.execute(player, args);
            break;
          case "가격":
          case "땅가격":
          case "기본가격":
          case "판매가":
            com.solo.sololand.command.island.DefaultIslandPrice.execute(player, args);
            break;
          default:
            int page;
            try{
              page = Integer.parseInt(args[0]);
            }catch(Exception e){
              this.sendHelp(player, MainCommand.HELP_ISLAND, 1);
              break;
            }
            this.sendHelp(player, MainCommand.HELP_ISLAND, page);
        }
        return true;



    }
    return false;
  }

  public void sendHelp(Player player, int type, int page){
    if(page < 1){
      page = 1;
    }
    if(type == MainCommand.HELP_LAND){
      if(page > 5){
        page = 5;
      }
      Message.help(player, "====== 땅 명령어 도움말 ( " + Integer.toString(page) + " / 5 페이지 ) ======");
      if(page <= 1){
        Message.help(player, "/땅 [페이지] - 땅 명령어 도움말을 표시합니다.");
        Message.help(player, "/땅 생성 - 땅을 생성합니다.");
        Message.help(player, "/땅 삭제 - 땅을 삭제합니다.");
        Message.help(player, "/땅 이동 [번호] - 해당 땅으로 이동합니다.");
        Message.help(player, "/땅 정보 - 서있는 위치에 있는 땅의 정보를 확인합니다.");
      }else if(page == 2){
        Message.help(player, "/땅 목록 또는 /땅 목록 [플레이어] - 땅 목록을 확인합니다.");
        Message.help(player, "/땅 구매 - 서있는 위치에 있는 땅을 구매합니다.");
        Message.help(player, "/땅 매물 - 판매중인 땅 목록을 봅니다.");
        Message.help(player, "/땅 환영말 [환영말..] - 땅 환영말을 설정합니다.");
        Message.help(player, "/땅 환영효과 [물방울/반짝/연기/하트/용암/불/제거] - 땅 환영 효과를 설정합니다.");
      }else if(page == 3){
        Message.help(player, "/땅 공유 [플레이어] - 땅을 공유합니다.");
        Message.help(player, "/땅 공유취소 [플레이어] - 땅 공유를 취소합니다.");
        Message.help(player, "/땅 출입금지 - 다른 유저가 땅에 출입하지 못하도록 합니다.");
        Message.help(player, "/땅 pvp - 땅 pvp 가능 여부를 설정합니다.");
        Message.help(player, "/땅 아이템드랍 - 다른 유저가 아이템을 주울 수 있는지 여부를 설정합니다.");
      }else if(page == 4){
        Message.help(player, "/땅 스폰 - 땅 이동 시 스폰 위치를 지정합니다.");
        Message.help(player, "/땅 판매 [가격] - 해당 가격으로 땅을 매물에 올립니다.");
        Message.help(player, "/땅 판매취소 - 땅 판매를 취소합니다.");
        Message.help(player, "/땅 양도 [플레이어] - 땅을 다른사람에게 양도합니다.");
        Message.help(player, "/땅 크기변경 - 땅의 크기를 변경합니다.");
      } else if(page >= 5){
        Message.help(player, "/땅 공유목록 또는 /땅 공유목록 [플레이어] - 공유받은 땅 목록을 표시합니다.");
        Message.help(player, "/땅 방문자 - 현재 땅에 방문한 유저를 표시합니다.");
      }
    }else if(type == HELP_WORLD){
      if(page > 3){
        page = 3;
      }
      Message.help(player, "====== 월드 명령어 도움말 ( " + Integer.toString(page) + " / 3 페이지 ) ======");
      if(page <= 1){
        Message.help(player, "/월드 정보 - 월드의 정보를 봅니다.");
        Message.help(player, "/월드 설정정보 - 월드의 설정값을 봅니다.");
        Message.help(player, "/월드 유저정보 - 월드의 유저 정보를 봅니다.");
        Message.help(player, "/월드 땅정보 - 월드의 땅 정보를 봅니다.");
        Message.help(player, "/월드 이름변경 [바꿀 이름] - 플러그인에서 사용될 월드 이름을 변경합니다.");
 
      }else if(page == 2){
        Message.help(player, "/월드 보호 - 월드의 블럭파괴 허용 여부를 설정합니다.");
        Message.help(player, "/월드 인벤세이브 - 월드의 인벤세이브 여부를 설정합니다.");
        Message.help(player, "/월드 pvp - 월드의 pvp 허용 여부를 설정합니다.");
        Message.help(player, "/월드 땅생성허용 - 월드에서 땅 생성을 허용할지 설정합니다.");
        Message.help(player, "/월드 기본땅가격 [가격] - 월드의 기본 땅 가격을 설정합니다.");
 
      }else if(page >= 3){
        Message.help(player, "/월드 땅최대길이 [길이(단위:블럭)] - 땅 생성시 최대 길이를 설정합니다.");
        Message.help(player, "/월드 땅최소길이 [길이(단위:블럭)] - 땅 생성시 최소 길이를 설정합니다.");
        Message.help(player, "/월드 블럭당가격 [가격] - 땅 생성시 블럭 당 가격을 설정합니다.");
        Message.help(player, "/월드 땅최대갯수 [갯수] - 땅 최대 소지 가능 갯수를 설정합니다.");
        Message.help(player, "/월드 폭발허용 - 월드의 폭발허용을 설정합니다.");
      }
        /*Message.help(player, "/월드 불번짐허용 - 월드의 불번짐 여부를 설정합니다.");*/
    }else if(type == MainCommand.HELP_ISLAND){
      if(page > 3){
        page = 3;
      }
      Message.help(player, "====== 섬 명령어 도움말 ( " + Integer.toString(page) + " / 1 페이지 ) ======");
      sendPage : {
        if(player.isOp()){
          Message.help(player, "/섬 시작 - 섬 월드를 생성합니다.");
        }
        Message.help(player, "/섬 구매 - 섬을 구매합니다.");
        Message.help(player, "/섬 목록 - 소유한 섬 목록을 봅니다.");
        Message.help(player, "/섬 이동 [섬 번호] - 해당 섬으로 이동합니다.");
        Message.help(player, "/섬 가격 [가격] - 섬의 기본 가격을 설정합니다.");
      }
    }
  }


}